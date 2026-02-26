package com.example.welan.model

import kotlinx.serialization.Serializable

@Serializable
data class sendchatDTO(
    val correoOrigen : String ,
    val correoDestino : String ,
    val message: String
)
