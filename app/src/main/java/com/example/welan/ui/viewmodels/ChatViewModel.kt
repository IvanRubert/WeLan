package com.example.welan.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.welan.model.ChatMessage
import com.example.welan.model.getChatMessages
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    private val _chat = mutableStateListOf<ChatMessage>()
    val chat = _chat

    private val _scrollToEnd = MutableSharedFlow<Unit>()
    val scrollToEnd = _scrollToEnd

    init {
        viewModelScope.launch {
            _chat.addAll(getChatMessages())
            _scrollToEnd.emit(Unit)
        }
    }

    fun addMessage(message: String, originUser: String){
        _chat.add(ChatMessage(originUser, message))
    }
}