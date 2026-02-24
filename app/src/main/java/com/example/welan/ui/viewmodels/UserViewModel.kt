package com.example.welan.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.welan.WeLanApplication
import com.example.welan.data.GaurdarUsuario
import com.example.welan.data.WeLanRepository
import com.example.welan.model.BusinessUserEdit
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserEdit
import com.example.welan.model.CommonUserWeLan
import com.example.welan.model.CreateCommonUserDTO
import com.example.welan.model.CreateBusinessUserDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed interface CommonUserUiState {
    data class Success(val data: CommonUserWeLan): CommonUserUiState
    data class Error(val error: Throwable): CommonUserUiState
    object Loading: CommonUserUiState
}

sealed interface BusinessUserUiState {
    data class Success(val data: BusinessUserWeLan): BusinessUserUiState
    data class Error(val error: Throwable): BusinessUserUiState
    object Loading: BusinessUserUiState
}

open class UserViewModel(open val weLanRepository: WeLanRepository): ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    var commonUserUiState: CommonUserUiState by mutableStateOf(CommonUserUiState.Loading)
        private set

    var businessUserUiState: BusinessUserUiState by mutableStateOf(BusinessUserUiState.Loading)
        private set

    fun getCommonUser(
        email: String,
    ){
        viewModelScope.launch {
            commonUserUiState = CommonUserUiState.Loading
            val repuesta = weLanRepository.getCommonUser(email)
            repuesta.fold(
                onSuccess = { user ->
                    commonUserUiState = CommonUserUiState.Success(user)
                },
                onFailure = { error ->
                    commonUserUiState = CommonUserUiState.Error(error)
                }
            )
        }
    }

    fun getCommonUserValidate(
        email: String,
        password: String,
        context: Context
    ){
        viewModelScope.launch {
            commonUserUiState = CommonUserUiState.Loading
            val repuesta = weLanRepository.getCommonUserValidate(email, password)
            repuesta.fold(
                onSuccess = { user ->
                    commonUserUiState = CommonUserUiState.Success(user)
                    GaurdarUsuario(context, email, "User")
                },
                onFailure = { error ->
                    commonUserUiState = CommonUserUiState.Error(error)
                    _errorMessage.value = "El usuario y la contraseña no conincide"
                }
            )
        }
    }

    fun postCommonUser(
        user: CreateCommonUserDTO
    ){
        viewModelScope.launch {
            val repuesta = weLanRepository.postCommonUser(user)
            if (!repuesta) {
                _errorMessage.value = "Error al registrarse"
            } else {

            }
        }
    }

    fun putCommonUser(
        user: CommonUserEdit
    ){
        viewModelScope.launch {
            commonUserUiState = CommonUserUiState.Loading
            val repuesta = weLanRepository.putCommonUser(user)
            repuesta.fold(
                onSuccess = { user ->
                    commonUserUiState = CommonUserUiState.Success(user)
                },
                onFailure = { error ->
                    commonUserUiState = CommonUserUiState.Error(error)
                }
            )
        }
    }

    fun putCommonUserPassword(
        email: String,
        password: String
    ){
        viewModelScope.launch {
            commonUserUiState = CommonUserUiState.Loading
            val repuesta = weLanRepository.putCommonUserPassword(email, password)
            repuesta.fold(
                onSuccess = { user ->
                    commonUserUiState = CommonUserUiState.Success(user)
                },
                onFailure = { error ->
                    commonUserUiState = CommonUserUiState.Error(error)
                }
            )
        }
    }

    fun getBusinessUser(
        email: String,
    ){
        viewModelScope.launch {
            businessUserUiState = BusinessUserUiState.Loading
            val repuesta = weLanRepository.getBusinessUser(email)
            repuesta.fold(
                onSuccess = { user ->
                    businessUserUiState = BusinessUserUiState.Success(user)
                },
                onFailure = { error ->
                    businessUserUiState = BusinessUserUiState.Error(error)
                }
            )
        }
    }

    fun getBusinessUserValidate(
        email: String,
        // Esta variable es del certificado
        organizacion: String,
        context: Context
    ){
        viewModelScope.launch {
            businessUserUiState = BusinessUserUiState.Loading
            val repuesta = weLanRepository.getBusinessUserValidate(email, organizacion)
            repuesta.fold(
                onSuccess = { user ->
                    businessUserUiState = BusinessUserUiState.Success(user)
                    GaurdarUsuario(context, email, "Business")
                },
                onFailure = { error ->
                    businessUserUiState = BusinessUserUiState.Error(error)
                    _errorMessage.value = "El usuario no coincide con el certificado"
                }
            )
        }
    }

    fun postBusinessUser(
        user: CreateBusinessUserDTO
    ){
        viewModelScope.launch {
            val repuesta = weLanRepository.postBusinessUser(user)
            if (!repuesta) {
                _errorMessage.value = "Error al registrarse"
            }
        }
    }

    fun putBusinessUser(
        user: BusinessUserEdit
    ){
        viewModelScope.launch {
            businessUserUiState = BusinessUserUiState.Loading
            val repuesta = weLanRepository.putBusinessUser(user)
            repuesta.fold(
                onSuccess = { user ->
                    businessUserUiState = BusinessUserUiState.Success(user)
                },
                onFailure = { error ->
                    businessUserUiState = BusinessUserUiState.Error(error)
                }
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeLanApplication)
                val weLanRepository = application.container.weLanRepository
                UserViewModel(weLanRepository = weLanRepository)
            }
        }
    }
}
