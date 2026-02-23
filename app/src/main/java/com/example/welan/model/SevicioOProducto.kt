package com.example.welan.model

import kotlinx.serialization.Serializable

enum class Unidad (val unidad: String) {
    HORAS("H"),
    KILOGRAMOS("Kg"),
    UNIDADES("U")
}

enum class Envio {NOENVIO, POSSIBILIDADENVIO, OBLIGACIONENVIO}


@Serializable
sealed class SerOProd {
    abstract val pathImage: String
    abstract val nombre: String
    abstract val correo: String
    abstract val descripcion: String
    abstract val precio: Double
    abstract val tipo_precio: Unidad
    abstract val envio: Envio
}

@Serializable
data class Servicio(
    override val pathImage: String,
    override val nombre: String,
    override val correo: String,
    override val descripcion: String,
    override val precio: Double,
    override val tipo_precio: Unidad,
    override val envio: Envio,
) : SerOProd()

@Serializable
data class Producto(
    override val pathImage: String,
    override val nombre: String,
    override val correo: String,
    override val descripcion: String,
    override val precio: Double,
    override val tipo_precio: Unidad,
    override val envio: Envio,
) : SerOProd()

@Serializable
data class ProductoOServicio(
    val pathImage: String,
    val nombre: String,
    val correo: String,
    val descripcion: String,
    val precio: Double,
    val tipo_precio: String,
    val envio: String,
    val tipo: String
)