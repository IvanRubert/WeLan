package com.example.welan.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.welan.data.ForoChat
import com.example.welan.data.getForoChats
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ForoViewModel: ViewModel() {
    private val _chat = mutableStateListOf<ForoChat>()
    val chat = _chat

    private val _scrollToEnd = MutableSharedFlow<Unit>()
    val scrollToEnd = _scrollToEnd

    init {
        viewModelScope.launch {
            _chat.addAll(getForoChats())
            _scrollToEnd.emit(Unit)
        }
    }

    fun addMessage(message: String, originUser: String){
        _chat.add(ForoChat(originUser, message))
        viewModelScope.launch {
            _scrollToEnd.emit(Unit)
        }
    }
}