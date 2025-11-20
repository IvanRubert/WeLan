package com.example.welan.data

import androidx.annotation.DrawableRes

data class User(
    @DrawableRes val imageRes: Int,
// Campo de imagen en la base de datos
//    val imageUrl: String,
    val name: String,
    val email: String,
    val occupation: String,
    val intereses: List<String>
)
