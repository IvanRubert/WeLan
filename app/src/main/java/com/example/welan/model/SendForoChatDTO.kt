package com.example.welan.model

import kotlinx.serialization.Serializable

@Serializable
data class SendForoChatDTO(
    val idForo : Int ,
    val correoOrigen : String ,
    val message: String
)
