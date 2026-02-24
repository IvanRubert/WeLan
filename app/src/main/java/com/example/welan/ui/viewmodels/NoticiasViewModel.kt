package com.example.welan.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.welan.WeLanApplication
import com.example.welan.data.WeLanRepository
import com.example.welan.model.NoticiDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface NoticiasUiState {
    data class Success(val data: List<NoticiDTO>): NoticiasUiState
    data class Error(val error: Throwable): NoticiasUiState
    object Loading: NoticiasUiState
}
class NoticiasViewModel(open val weLanRepository: WeLanRepository) : ViewModel() {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    var noticiaUiState : NoticiasUiState by mutableStateOf(NoticiasUiState.Loading)

    fun getNoticias(){
        viewModelScope.launch {
            noticiaUiState = NoticiasUiState.Loading
            val respuesta = weLanRepository.getNoticias()
            respuesta.fold(
                onSuccess = { noticia ->
                    noticiaUiState = NoticiasUiState.Success(noticia)
                },
                onFailure = { error ->
                    noticiaUiState = NoticiasUiState.Error(error)
                }
            )
        }
    }
    init {
        getNoticias()
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WeLanApplication)
                val weLanRepository = application.container.weLanRepository
                NoticiasViewModel(weLanRepository = weLanRepository)
            }
        }
    }
}