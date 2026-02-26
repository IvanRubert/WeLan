package com.example.welan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class ForoChatDTO(
    var correo : String,
    var mensaje : String ?,
    @SerialName("fecha")
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateLastMessage: LocalDateTime ? 
)

@Serializable
data class UserWelanDTO(
    val pathImage: String?,
    val nombre: String,
    val correo: String
)