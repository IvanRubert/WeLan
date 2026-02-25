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
    // Usamos ISO_DATE_TIME para soportar el offset (+00:00)
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): LocalDateTime {
        val string = decoder.decodeString()
        // Parseamos considerando el offset y convertimos a Local
        return java.time.OffsetDateTime.parse(string, formatter).toLocalDateTime()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.atOffset(java.time.ZoneOffset.UTC).format(formatter))
    }
}

data class ChatMessage(
    val origin: String,
    val message: String
)

fun getChatMessages(): List<ChatMessage>{
    return listOf(
        ChatMessage("Pepe Gil", "Hola, como estas?"),
        ChatMessage("Carlos García", "Bien, y tu?"),
        ChatMessage("Carlos García", "Como va el trabajo?"),
        ChatMessage("Pepe Gil", "Es un poco estresante como siempre"),
        ChatMessage("Pepe Gil", "pero me adapto bien"),
        ChatMessage("Carlos García", "Quedamos para tomar algo?")
    )
}