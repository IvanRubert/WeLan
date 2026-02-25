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
    abstract var pathImage: String
    abstract val nombre: String
    abstract val correo: String
    abstract val descripcion: String
    abstract val precio: Float
    abstract val tipo_precio: Unidad
    abstract val envio: Envio
}

@Serializable
data class Servicio(
    override var pathImage: String,
    override val nombre: String,
    override val correo: String,
    override val descripcion: String,
    override val precio: Float,
    override val tipo_precio: Unidad,
    override val envio: Envio,
) : SerOProd()

@Serializable
data class Producto(
    override var pathImage: String,
    override val nombre: String,
    override val correo: String,
    override val descripcion: String,
    override val precio: Float,
    override val tipo_precio: Unidad,
    override val envio: Envio,
) : SerOProd()

@Serializable
data class ProductoOServicio(
    val precio: Float,
    val tipo: String,
    val tipo_precio: String,
    val img_path: String?,
    val descripcion: String?,
    val envio: String,
    val correo: String,
    val nombre: String
)