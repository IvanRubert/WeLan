package com.example.welan.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.welan.R

data class Noticia(
    @StringRes var Titulo: Int,
    @DrawableRes var Photo: Int,
    @StringRes var Descripción: Int,
)

fun getNoticias(): List<Noticia> {
    return listOf(
        Noticia(
            Titulo = R.string.noticia3_titulo,
            Photo = R.drawable.usuarios_nuevos,
            Descripción = R.string.noticia3_descripcion
        ),
        Noticia(
            Titulo = R.string.noticia2_titulo,
            Photo = R.drawable.puntuacion,
            Descripción = R.string.noticia2_descripcion
        ),
        Noticia(
            Titulo = R.string.noticia1_titulo,
            Photo = R.drawable.actualizacion001,
            Descripción = R.string.noticia1_descripcion
        )
    )
}