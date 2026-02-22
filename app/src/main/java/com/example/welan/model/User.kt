package com.example.welan.model

import androidx.annotation.DrawableRes

data class User(
    @DrawableRes val imageRes: Int,
    // Pruebas type User || Business
    val type: String = "User",
// Campo de imagen en la base de datos
//    val imageUrl: String,
    val name: String,
    val email: String,
    val descripcion: String? = null,
    val intereses: List<String>
)