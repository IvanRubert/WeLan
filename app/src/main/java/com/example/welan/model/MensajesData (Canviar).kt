package com.example.welan.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class MensajesData @RequiresApi(Build.VERSION_CODES.O) constructor(
    val img_conversation: String,
    val nombre: String,
    val correo: String,

    @SerialName("lastMessage") // <--- Forzamos que lea "lastMessage" del JSON
    val lastMessage: String,

    @SerialName("fecha")
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateLastMessage: LocalDateTime // Esta será tu fecha real
)

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    // Usamos el formateador flexible por defecto
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): LocalDateTime {
        val string = decoder.decodeString()
        return try {
            // Intento 1: Si trae Offset (ej. 2026-02-22T00:00:00Z)
            java.time.OffsetDateTime.parse(string).toLocalDateTime()
        } catch (e: Exception) {
            // Intento 2: Si es una fecha local pura (ej. 2026-02-22T00:00:00)
            java.time.LocalDateTime.parse(string)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        // Al serializar, tú decides si quieres guardarlo con o sin la 'Z'
        // Para consistencia con lo que recibes, podrías usar simplemente:
        encoder.encodeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
}

data class ChatMessage(
    val origin: String,
    val message: String
)

