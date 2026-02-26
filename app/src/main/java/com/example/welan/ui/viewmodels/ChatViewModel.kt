package com.example.welan.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.welan.WeLanApplication
import com.example.welan.data.WeLanRepository
import com.example.welan.model.ChatMessage
import com.example.welan.model.NoticiDTO
import com.example.welan.model.chatDTO
import com.example.welan.model.sendchatDTO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime


sealed interface ChatUiState {
    data class Success(val data: List<chatDTO>): ChatUiState
    data class Error(val error: Throwable): ChatUiState
    object Loading: ChatUiState
}


class ChatViewModel(open val weLanRepository: WeLanRepository): ViewModel() {
    private val _chat = mutableStateListOf<chatDTO>()
    val chat : List<chatDTO> = _chat
    lateinit var _nuevoMensaje : chatDTO
    var chatUiState : ChatUiState by mutableStateOf(ChatUiState.Loading)
    private val _scrollToEnd = MutableSharedFlow<Unit>()
    val scrollToEnd = _scrollToEnd

    var correo1 : String = ""
    var correo2 : String = ""

    fun getChat() {
        if (correo2.isEmpty()) return
        val email2 = correo2
        viewModelScope.launch {
            chatUiState = ChatUiState.Loading
            val respuesta = weLanRepository.getChat(correo1, email2)
            respuesta.fold(
                onSuccess = { lista ->
                    _chat.clear()
                    _chat.addAll(lista)
                    chatUiState = ChatUiState.Success(_chat)
                },
                onFailure = { error ->
                    if (_chat.isEmpty()) {
                        chatUiState = ChatUiState.Error(error)
                    } else {
                        chatUiState = ChatUiState.Success(_chat)
                    }
                }
            )
            while(isActive){
                delay(5000)
                val respuesta = weLanRepository.getChat(correo1, email2)
                respuesta.fold(
                    onSuccess = { lista ->
                        _chat.clear()
                        _chat.addAll(lista)
                        chatUiState = ChatUiState.Success(_chat)
                    },
                    onFailure = { error ->
                        if (_chat.isEmpty()) {
                            chatUiState = ChatUiState.Error(error)
                        } else {
                            chatUiState = ChatUiState.Success(_chat)
                        }
                    }
                )
            }
        }
    }


    private fun sincLista(new : List<chatDTO>){
        if(_chat.size != new.size){
            _chat.clear()
            _chat.addAll(new)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postChat(mensaje : String){
        viewModelScope.launch {
            _nuevoMensaje = chatDTO(
                correo = correo1,
                mensaje = mensaje,
                dateLastMessage = LocalDateTime.now()
            )

            _chat.add(_nuevoMensaje)

            viewModelScope.launch {
                try {
                    val mensajePost = sendchatDTO(correo1,correo2!!,mensaje)
                    weLanRepository.postChat(mensajePost)
                } catch (e : Exception){
                    _chat.remove(_nuevoMensaje)
                }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeLanApplication)
                val weLanRepository = application.container.weLanRepository
                ChatViewModel(weLanRepository = weLanRepository)
            }
        }
    }
}