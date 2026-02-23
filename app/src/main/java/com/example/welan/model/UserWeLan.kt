package com.example.welan.model

import kotlinx.serialization.Serializable


@Serializable
sealed class UserWelan {
    abstract val pathImage: String
    abstract val nombre: String
    abstract val correo: String
}

@Serializable
data class CommonUserWeLan(
    override val pathImage: String,
    override val nombre: String,
    override val correo: String,
    val intereses: List<Interes>?,
    val passHash: String
) : UserWelan()

@Serializable
data class BusinessUserWeLan(
    override val pathImage: String,
    override val nombre: String,
    override val correo: String,
    val certificado: String,
    val descripcion: String? = null,
    val servicios: List<String>?
) : UserWelan()