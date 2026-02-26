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
import com.example.welan.model.ForoChat
import com.example.welan.model.ForoChatDTO
import com.example.welan.model.getForoChats
import com.example.welan.model.sendchatDTO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime

sealed interface ForoChatUiState {
    data class Success(val data: List<ForoChatDTO>): ForoChatUiState
    data class Error(val error: Throwable): ForoChatUiState
    object Loading: ForoChatUiState
}

class ForoChatViewModel(open val weLanRepository: WeLanRepository): ViewModel() {
    private val _chat = mutableStateListOf<ForoChatDTO>()
    val chat = _chat

    lateinit var _nuevoMensaje : ForoChatDTO

    var chatUiState : ForoChatUiState by mutableStateOf(ForoChatUiState.Loading)

    private val _scrollToEnd = MutableSharedFlow<Unit>()
    val scrollToEnd = _scrollToEnd

    var foroId : Int = 0
    var correo : String = ""

    fun getChat(){
        viewModelScope.launch {
            chatUiState = ForoChatUiState.Loading
            val respuesta = weLanRepository.getForoChat(foroId)
            respuesta.fold(
                onSuccess = { lista ->
                    _chat.clear()
                    _chat.addAll(lista)
                    chatUiState = ForoChatUiState.Success(_chat)
                },
                onFailure = { error ->
                    if (_chat.isEmpty()) {
                        chatUiState = ForoChatUiState.Error(error)
                    } else {
                        chatUiState = ForoChatUiState.Success(_chat)
                    }
                }
            )
            while(isActive){
                delay(5000)
                val respuesta = weLanRepository.getForoChat(foroId)
                respuesta.fold(
                    onSuccess = { lista ->
                        _chat.clear()
                        _chat.addAll(lista)
                        chatUiState = ForoChatUiState.Success(_chat)
                    },
                    onFailure = { error ->
                        if (_chat.isEmpty()) {
                            chatUiState = ForoChatUiState.Error(error)
                        } else {
                            chatUiState = ForoChatUiState.Success(_chat)
                        }
                    }
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postChat(mensaje : String){
        viewModelScope.launch {
            _nuevoMensaje = ForoChatDTO(
                correo = correo,
                mensaje = mensaje,
                dateLastMessage = LocalDateTime.now()
            )

            _chat.add(_nuevoMensaje)

            viewModelScope.launch {
                try {
                    val mensajePost = sendchatDTO(correo,correo2!!,mensaje)
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
                ForoChatViewModel(weLanRepository = weLanRepository)
            }
        }
    }
}
