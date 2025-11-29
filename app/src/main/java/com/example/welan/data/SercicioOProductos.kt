package com.example.welan.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.welan.R

enum class Unidad (unidad: String) {
    HORAS("h"),
    KILOGRAMOS("Kg"),
    UNIDADES("U")
}

enum class ServOProd { SERVICIO, PRODUCTO}
data class ServicioOProductos (
    var Servoprod: ServOProd,
    @StringRes var Titulo: Int,
    @DrawableRes var Photo: Int,
    @StringRes var Descripción: Int,
    var Envio: Boolean? = null,
    var Precio: Double,
    var Unidad: Unidad
)

fun getServicioProductos(): List<ServicioOProductos> {
    return listOf(
        ServicioOProductos(
            Servoprod = ServOProd.SERVICIO,
            Titulo = R.string.servicioproducto1_titulo,
            Photo = R.drawable.serviciospintura,
            Descripción = R.string.servicioproducto1_descripcion,
            Envio = null,
            Precio = 9.99,
            Unidad = Unidad.HORAS
        ),
        ServicioOProductos(
            Servoprod = ServOProd.PRODUCTO,
            Titulo = R.string.servicioproducto2_titulo,
            Photo = R.drawable.tomates,
            Descripción = R.string.servicioproducto3_descripcion,
            Envio = false,
            Precio = 2.00,
            Unidad = Unidad.KILOGRAMOS
        ),
        ServicioOProductos(
            Servoprod = ServOProd.SERVICIO,
            Titulo = R.string.servicioproducto3_titulo,
            Photo = R.drawable.serviciolimpieza,
            Descripción = R.string.servicioproducto3_descripcion,
            Envio = null,
            Precio = 10.00,
            Unidad = Unidad.HORAS
        ),
        ServicioOProductos(
            Servoprod = ServOProd.PRODUCTO,
            Titulo = R.string.servicioproducto4_titulo,
            Photo = R.drawable.bolsadehielo,
            Descripción = R.string.servicioproducto4_descripcion,
            Envio = true,
            Precio = 1.05,
            Unidad = Unidad.UNIDADES
        ),
    )
}