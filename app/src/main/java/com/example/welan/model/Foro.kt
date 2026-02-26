package com.example.welan.model

import kotlinx.serialization.Serializable

@Serializable
data class Foro(
    val id_foro: Int,
    val titulo: String,
    val pathImage: String,
    val intereses: List<Interes>?,
    val administradores: List<String>?,
    val total_mensajes: Long
)

@Serializable
data class ForoDTO(
    val titulo: String,
    val pathImage: String,
    val descripcion: String?,
    val intereses: List<Interes>?,
    val administradores: List<String>?,
)