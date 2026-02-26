package com.example.welan.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.welan.WeLanApplication
import com.example.welan.data.WeLanRepository
import com.example.welan.model.MensajesData
import com.example.welan.ui.screens.Mensajes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime


sealed interface MensajesUiState {
    data class Success(val data: List<MensajesData>): MensajesUiState
    data class Error(val error: Throwable): MensajesUiState
    object Loading: MensajesUiState
}
class MessagesViewModel( correo: String,open val weLanRepository: WeLanRepository) : ViewModel() {

    var mensajesUiState : MensajesUiState by mutableStateOf(MensajesUiState.Loading)
    var _correo : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    var mensajeSelected : MensajesData = MensajesData(
        img_conversation = "",
        nombre = "",
        correo = "",
        lastMessage = "",
        dateLastMessage = LocalDateTime.now()
    )
    init {
        getMensajes(correo)
        _correo = correo
    }
    fun getMensajes(correo : String){
        viewModelScope.launch {
            mensajesUiState = MensajesUiState.Loading
            val respuesta = weLanRepository.getMensajes(correo)
            respuesta.fold(
                onSuccess = { mensajes ->
                    mensajesUiState = MensajesUiState.Success(mensajes)
                },
                onFailure = { error ->
                    mensajesUiState = MensajesUiState.Error(error)
                }
            )
        }
    }
    companion object {
        fun provideFactory(correo: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeLanApplication)

                val weLanRepository = application.container.weLanRepository

                MessagesViewModel(
                    weLanRepository = weLanRepository,
                    correo = correo
                )
            }
        }
    }
}