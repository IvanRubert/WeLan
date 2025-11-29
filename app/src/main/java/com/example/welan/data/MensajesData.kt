package com.example.welan.data

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
