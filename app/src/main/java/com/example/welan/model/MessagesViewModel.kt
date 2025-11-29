package com.example.welan.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.welan.data.MensajesData
import com.example.welan.data.getMessages

class MessagesViewModel : ViewModel() {
    val messages = mutableStateListOf<MensajesData>()

    init {
        messages.addAll(getMessages())
    }

    fun markAsRead(index: Int) {
        messages[index] = messages[index].copy(msgNoRead = false)
    }
}