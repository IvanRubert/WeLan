package com.example.welan.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.welan.R

enum class Unidad (val unidad: String) {
    HORAS("H"),
    KILOGRAMOS("Kg"),
    UNIDADES("U")
}

enum class ServOProd { SERVICIO, PRODUCTO}
enum class Envio {NOENVIO, POSSIBILIDADENVIO, OBLIGACIONENVIO}
data class ServicioOProductos (
    var Servoprod: ServOProd,
    @StringRes var Titulo: Int,
    @DrawableRes var Photo: Int,
    @StringRes var Descripcion: Int,
    var Envio: Envio,
    var Precio: Double,
    var Unidad: Unidad
)

fun getServicioProductos(): List<ServicioOProductos> {
    return listOf(
        ServicioOProductos(
            Servoprod = ServOProd.SERVICIO,
            Titulo = R.string.servicioproducto1_titulo,
            Photo = R.drawable.serviciospintura,
            Descripcion = R.string.servicioproducto1_descripcion,
            Envio = Envio.NOENVIO,
            Precio = 9.99,
            Unidad = Unidad.HORAS
        ),
        ServicioOProductos(
            Servoprod = ServOProd.PRODUCTO,
            Titulo = R.string.servicioproducto2_titulo,
            Photo = R.drawable.tomates,
            Descripcion = R.string.servicioproducto3_descripcion,
            Envio = Envio.POSSIBILIDADENVIO,
            Precio = 2.00,
            Unidad = Unidad.KILOGRAMOS
        ),
        ServicioOProductos(
            Servoprod = ServOProd.SERVICIO,
            Titulo = R.string.servicioproducto3_titulo,
            Photo = R.drawable.serviciolimpieza,
            Descripcion = R.string.servicioproducto3_descripcion,
            Envio = Envio.NOENVIO,
            Precio = 10.00,
            Unidad = Unidad.HORAS
        ),
        ServicioOProductos(
            Servoprod = ServOProd.PRODUCTO,
            Titulo = R.string.servicioproducto4_titulo,
            Photo = R.drawable.bolsadehielo,
            Descripcion = R.string.servicioproducto4_descripcion,
            Envio = Envio.OBLIGACIONENVIO,
            Precio = 1.05,
            Unidad = Unidad.UNIDADES
        ),
        ServicioOProductos(
            Servoprod = ServOProd.SERVICIO,
            Titulo = R.string.servicioproducto3_titulo,
            Photo = R.drawable.serviciolimpieza,
            Descripcion = R.string.servicioproducto3_descripcion,
            Envio = Envio.NOENVIO,
            Precio = 40.00,
            Unidad = Unidad.UNIDADES
        ),
    )
}