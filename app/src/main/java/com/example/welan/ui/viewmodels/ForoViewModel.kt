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
import com.example.welan.model.Foro
import com.example.welan.model.ForoDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface ForoUiState {
    data class Success(val data: List<Foro>): ForoUiState
    data class Error(val error: Throwable): ForoUiState
    object Loading: ForoUiState
}

open class ForoViewModel(open val weLanRepository: WeLanRepository): ViewModel() {

    var foroUiState: ForoUiState by mutableStateOf(ForoUiState.Loading)
        private set

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var _foroSelected: MutableStateFlow<Foro> = MutableStateFlow(Foro(0,"", "", emptyList(), emptyList(), 0))
    val foroSelected = _foroSelected.asStateFlow()

    fun setForoSelected(foro: Foro){
        _foroSelected.value = foro
    }

    init {
        getForoFiltro("")
    }

    fun getForo(){
        viewModelScope.launch {
            foroUiState = ForoUiState.Loading
            val repuesta = weLanRepository.getForo()
            repuesta.fold(
                onSuccess = { foro ->
                    foroUiState = ForoUiState.Success(foro)
                },
                onFailure = { error ->
                    foroUiState = ForoUiState.Error(error)
                }
            )
        }
    }

    fun getForoFiltro(nombre: String){
        if (nombre == "") {
            getForo()
        } else {
            viewModelScope.launch {
                foroUiState = ForoUiState.Loading
                val repuesta = weLanRepository.getForoFiltro(nombre)
                repuesta.fold(
                    onSuccess = { serOProd ->
                        foroUiState = ForoUiState.Success(serOProd)
                    },
                    onFailure = { error ->
                        foroUiState = ForoUiState.Error(error)
                    }
                )
            }
        }
    }

    fun postForo(foro: ForoDTO){
        viewModelScope.launch {
            foroUiState = ForoUiState.Loading
            val repuesta = weLanRepository.postForo(foro)
            getForo()
            if (!repuesta) {
                _errorMessage.value = "No se a podido crear el foro"
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeLanApplication)
                val weLanRepository = application.container.weLanRepository
                ForoViewModel(weLanRepository = weLanRepository)
            }
        }
    }
}
