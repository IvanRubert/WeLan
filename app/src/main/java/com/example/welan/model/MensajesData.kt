package com.example.welan.model

import androidx.annotation.DrawableRes
import com.example.welan.R

data class MensajesData(
    @DrawableRes val imageUserRef: Int,
    val nameUser: String,
    val msgNoRead: Boolean = false
)

fun getMessages(): List<MensajesData>{
    return listOf(
        MensajesData(R.drawable.welan_logo, "Ana Lisa Pérez"),
        MensajesData(R.drawable.welan_logo, "Teresa Martínez", true),
        MensajesData(R.drawable.actualizacion001, "Alejandro Gil"),
        MensajesData(R.drawable.welan_logo, "Manuel Sánchez", true),
        MensajesData(R.drawable.usuarios_nuevos, "Carlos García", true),
        MensajesData(R.drawable.usuarios_nuevos, "Carlos GarcíaGarcíaGarcíaGarcíaGarcíaGarcíaGarcía", true),
    )
}

data class ChatMessage(
    val origin: String,
    val message: String
)

fun getChatMessages(): List<ChatMessage>{
    return listOf(
        ChatMessage("Pepe Gil", "Hola, como estas?"),
        ChatMessage("Carlos García", "Bien, y tu?"),
        ChatMessage("Carlos García", "Como va el trabajo?"),
        ChatMessage("Pepe Gil", "Es un poco estresante como siempre"),
        ChatMessage("Pepe Gil", "pero me adapto bien"),
        ChatMessage("Carlos García", "Quedamos para tomar algo?")
    )
}