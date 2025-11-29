package com.example.welan.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.welan.data.ChatMessage
import com.example.welan.data.MensajesData
import com.example.welan.data.getChatMessages

class ChatViewModel: ViewModel() {
    val chat = mutableStateListOf<ChatMessage>()

    init {
        chat.addAll(getChatMessages())
    }

    fun addMessage(message: String, originUser: String){
        chat.add(ChatMessage(originUser, message))
    }
}