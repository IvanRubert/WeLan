package com.example.welan.model

import kotlinx.serialization.Serializable


@Serializable
sealed class UserWelan {
    abstract var pathImage: String
    abstract val nombre: String
    abstract val correo: String
}

@Serializable
data class CommonUserWeLan(
    override var pathImage: String,
    override val nombre: String,
    override val correo: String,
    val intereses: List<Interes>?,
    val passHash: String
) : UserWelan()

@Serializable
data class BusinessUserWeLan(
    override var pathImage: String,
    override val nombre: String,
    override val correo: String,
    val certificado: String,
    val descripcion: String? = null,
    val servicios: List<String>?
) : UserWelan()

@Serializable
data class CommonUserEdit(
    val nombre: String,
    val correo: String,
    val pathImage: String?,
    val interesList: List<Interes>
)

@Serializable
data class BusinessUserEdit(
    val nombre: String,
    val correo: String,
    val pathImage: String?,
    val descripcion: String,
    val serviciosList: List<String>
)

@Serializable
data class CreateCommonUserDTO(
    val nombre: String,
    val correo: String,
    var passHash: String
)
@Serializable
data class CreateBusinessUserDTO(
    val nombre: String,
    val correo: String,
    val certificado: String
)