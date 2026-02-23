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
import com.example.welan.model.SerOProd
import kotlinx.coroutines.launch


sealed interface SerOProdUiState {
    data class Success(val data: List<SerOProd>): SerOProdUiState
    data class Error(val error: Throwable): SerOProdUiState
    object Loading: SerOProdUiState
}

open class SerOProdViewModel(open val weLanRepository: WeLanRepository): ViewModel() {

    var serOProdUiState: SerOProdUiState by mutableStateOf(SerOProdUiState.Loading)
        private set

    init {
        getSerOProd()
    }

    fun getSerOProd(
    ){
        viewModelScope.launch {
            serOProdUiState = SerOProdUiState.Loading
            val repuesta = weLanRepository.getSerOProd()
            repuesta.fold(
                onSuccess = { user ->
                    serOProdUiState = SerOProdUiState.Success(user)
                },
                onFailure = { error ->
                    serOProdUiState = SerOProdUiState.Error(error)
                }
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeLanApplication)
                val weLanRepository = application.container.weLanRepository
                SerOProdViewModel(weLanRepository = weLanRepository)
            }
        }
    }
}
