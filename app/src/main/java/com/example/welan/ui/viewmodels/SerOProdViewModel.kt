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
import com.example.welan.model.Envio
import com.example.welan.model.ProductoOServicio
import com.example.welan.model.SerOProd
import com.example.welan.model.Servicio
import com.example.welan.model.Unidad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface SerOProdUiState {
    data class Success(val data: List<SerOProd>): SerOProdUiState
    data class Error(val error: Throwable): SerOProdUiState
    object Loading: SerOProdUiState
}

open class SerOProdViewModel(open val weLanRepository: WeLanRepository): ViewModel() {

    var serOProdUiState: SerOProdUiState by mutableStateOf(SerOProdUiState.Loading)
        private set

    private var _seroprodSelected: MutableStateFlow<SerOProd> = MutableStateFlow(Servicio(null,"", "", "", 0.00f, Unidad.UNIDADES, Envio.POSSIBILIDADENVIO))
    val serOProdSelected = _seroprodSelected.asStateFlow()

    fun setSerOProdSelected(seroprod: SerOProd){
        _seroprodSelected.value = seroprod
    }

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        getSerOProdFiltro("")
    }

    fun getSerOProd(){
        viewModelScope.launch {
            serOProdUiState = SerOProdUiState.Loading
            val repuesta = weLanRepository.getSerOProd()
            repuesta.fold(
                onSuccess = { serOProd ->
                    serOProdUiState = SerOProdUiState.Success(serOProd)
                },
                onFailure = { error ->
                    serOProdUiState = SerOProdUiState.Error(error)
                }
            )
        }
    }

    fun getSerOProdFiltro(nombre: String){
        if (nombre == "") {
            getSerOProd()
        } else {
            viewModelScope.launch {
                serOProdUiState = SerOProdUiState.Loading
                val repuesta = weLanRepository.getSerOProdFiltro(nombre)
                repuesta.fold(
                    onSuccess = { serOProd ->
                        serOProdUiState = SerOProdUiState.Success(serOProd)
                    },
                    onFailure = { error ->
                        serOProdUiState = SerOProdUiState.Error(error)
                    }
                )
            }
        }
    }

    fun postSerOProd(
        serOProd: ProductoOServicio
    ){
        viewModelScope.launch {
            val repuesta = weLanRepository.postSerOProd(serOProd)
            if (!repuesta) {
                if (serOProd.tipo == "Producto")  {
                    _errorMessage.value = "Error al crear el producto"
                } else {
                    _errorMessage.value = "Error al crear el servicio"
                }
            }
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
