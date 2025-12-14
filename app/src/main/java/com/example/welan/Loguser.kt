package com.example.welan

import android.content.Context
import com.example.welan.data.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Usuario(val name: String, val email: String, val descripcion: String?, val intereses: List<String>, val type: String)


fun UserExist(context: Context): Boolean {
    var dato: Usuario

    val jsonString = context.assets.open("usuario.json")
        .bufferedReader()
        .use { it.readText() }

    dato = Json.decodeFromString<Usuario>(jsonString)

    return if (dato.type != "") {
        true
    } else {
        false
    }
}
fun UsuarioJson(context: Context): User {
    var user: User
    val imagen = R.drawable.welan_logo
    var name = "NameDefault"
    var email = "emaildefault@default.def"
    var descripcion: String?
    var intereses: List<String>
    var type: String

    val jsonString = context.assets.open("usuario.json")
        .bufferedReader()
        .use { it.readText() }

    val dato = Json.decodeFromString<Usuario>(jsonString)

    if (dato.name != "") {
        name = dato.name
    }
    if (dato.email != "") {
        email = dato.email
    }
    if (dato.descripcion == "" && dato.type == "User") {
        descripcion = null
    } else {
        descripcion = dato.descripcion
    }
    intereses = dato.intereses
    type = dato.type

    user = User(
        imageRes = imagen,
        name = name,
        email = email,
        descripcion = descripcion,
        intereses = intereses,
        type = type
    )
    return user
}