package com.example.welan.ui.componentes

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.welan.R
import com.example.welan.model.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Usuario(val email: String, val type:String)


fun UserExist(context: Context): Boolean {
    var dato: Usuario

    val jsonString = context.assets.open("usuario.json")
        .bufferedReader()
        .use { it.readText() }

    dato = Json.decodeFromString<Usuario>(jsonString)

    return if (dato.email != "") {
        true
    } else {
        false
    }
}

fun UserType(context: Context): String {
    var dato: Usuario

    val jsonString = context.assets.open("usuario.json")
        .bufferedReader()
        .use { it.readText() }

    dato = Json.decodeFromString<Usuario>(jsonString)

    return dato.type
}

fun UsuarioJson(context: Context):  String {
    var user: String

    val jsonString = context.assets.open("usuario.json")
        .bufferedReader()
        .use { it.readText() }

    val dato = Json.decodeFromString<Usuario>(jsonString)
    user = dato.email

    return user
}