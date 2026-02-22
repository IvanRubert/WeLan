package com.example.welan.ui.viewmodels

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
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
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
        password: String
    ){
        viewModelScope.launch {
            commonUserUiState = CommonUserUiState.Loading
            val repuesta = weLanRepository.getCommonUserValidate(email, password)
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
        organizacion: String
    ){
        viewModelScope.launch {
            businessUserUiState = BusinessUserUiState.Loading
            val repuesta = weLanRepository.getBusinessUserValidate(email, organizacion)
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
