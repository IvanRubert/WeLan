package com.example.welan

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Usuario(val name: String, val email: String, val descripcion: String, val intereses: List<String>, val type: String)

@Composable
fun UsuarioJson() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val jsonString = context.assets.open("usuario.json")
            .bufferedReader()
            .use { it.readText() }

        val dato = Json.decodeFromString<Usuario>(jsonString)

        Log.d(
            "prueba", "Nombre: ${dato.name}, Email: ${dato.email}, " +
                    "Descripcion.: ${dato.descripcion}, Intereses:" +
                    "${dato.intereses} Tipo: ${dato.type}"
        )
    }
}