package com.example.welan.model


import kotlinx.serialization.Serializable

@Serializable
data class NoticiDTO(
    var idNoticias : Int,
    var titulo : String,
    var photo : String,
    var descripcion : String
)