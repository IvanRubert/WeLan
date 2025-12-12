package com.example.welan

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Serializable
data class Usuario(val name: String, val email: String, val descripcion: String, val intereses: List<String>, val type: String)

@RequiresApi(Build.VERSION_CODES.O)
fun UsuarioJson() {
    val archivoPath: Path = Paths.get("data/usuario.json")

    if (Files.exists(archivoPath)) {
        try {
            if (Files.isReadable(archivoPath)) {
                try {
                    val jsonString = Files.readString(archivoPath)

                    Json.decodeFromString<List<Usuario>>(jsonString)
                }catch (e: Exception) {
                    println("Error: El archivo $archivoPath no esta bien formado")
                }
            }
        } catch (e: Exception) {

        }
    }
}