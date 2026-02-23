package com.example.welan.model

import kotlinx.serialization.Serializable

@Serializable
data class Interes(
    var nombre: String,
    val color: Long
)