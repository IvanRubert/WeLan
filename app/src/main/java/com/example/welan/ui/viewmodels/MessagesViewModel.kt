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
import com.example.welan.model.MensajesDataCreateDTO
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime

sealed interface CorreoUiSate {
    data class Success(val data: Boolean): CorreoUiSate
    data class Error(val error: Boolean): CorreoUiSate
    object Loading: CorreoUiSate
}
sealed interface MensajesUiState {
    data class Success(val data: List<MensajesData>): MensajesUiState
    data class Error(val error: Throwable): MensajesUiState
    object Loading: MensajesUiState
}
class MessagesViewModel( correo: String,open val weLanRepository: WeLanRepository) : ViewModel() {

    var correoUiSate : CorreoUiSate by mutableStateOf(CorreoUiSate.Loading)
    var mensajesUiState : MensajesUiState by mutableStateOf(MensajesUiState.Loading)
    var _correo : String = ""

    var error : Boolean = false
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
        refresMessages(correo)
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

    fun refresMessages(correo : String){
        viewModelScope.launch {
            while (isActive) {
                delay(5000)
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
    }
    suspend fun validarCorreoYMensaje(correo2: String): Pair<Boolean, Boolean> {
        return try {
            val existe = weLanRepository
                .validateCorreo(correo2)
                .getOrElse { return false to false }

            if (!existe) {
                false to false
            } else {
                val tieneMensaje = weLanRepository
                    .validateMensaje(_correo, correo2)
                    .getOrElse { false }
                true to tieneMensaje
            }
        } catch (_: Exception) {
            false to false
        }
    }

    suspend fun createMessage(new : String){
         try {
            val conversation : MensajesDataCreateDTO = MensajesDataCreateDTO(
                correoOrigen = _correo,
                correoDestino = new
            )

            weLanRepository.postMessage(conversation)
             getMensajes(_correo)
        } catch (e: Exception) {
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