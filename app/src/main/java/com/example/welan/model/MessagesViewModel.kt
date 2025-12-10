package com.example.welan.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.welan.data.MensajesData
import com.example.welan.data.getMessages

class MessagesViewModel : ViewModel() {
    private val _messages = mutableStateListOf<MensajesData>()
    val messages = _messages

    init {
        _messages.addAll(getMessages())
    }

    fun markAsRead(index: Int) {
        _messages[index] = _messages[index].copy(msgNoRead = false)
    }
}