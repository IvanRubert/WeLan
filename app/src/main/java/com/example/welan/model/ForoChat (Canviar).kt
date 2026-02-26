package com.example.welan.model

import kotlinx.serialization.Serializable


@Serializable
data class SendForoMessageDTO(
    val idForo: Int ,
    val correoOrigen: String,
    val message: String
)

data class ForoChat(
    val origin: String,
    val message: String
)

fun getForoChats(): List<ForoChat> {
    return listOf(
        ForoChat("Carlos", "¿Qué opináis sobre el tema de esta semana?"),
        ForoChat("Lucia", "Acabo de ver el hilo y me pareció interesante comentar."),
        ForoChat("Miguel", "¡Buenas a todos! ¿Cómo va el día?"),
        ForoChat("Ana", "Solo paso a saludar, hace tiempo que no escribía."),
        ForoChat("Rober", "¿Alguien ha probado algo nuevo últimamente?"),
        ForoChat("Diana", "Me hizo gracia lo que comentaron en otro hilo."),
        ForoChat("Pepe", "Estoy leyendo vuestras respuestas, muy entretenidas."),
        ForoChat("Sergio", "Abro este tema para ver qué piensa la comunidad."),
        ForoChat("Nerea", "Me quedo por aquí a ver cómo avanza la conversación."),
        ForoChat("Javi", "Gracias por compartir, siempre es interesante leeros."),
        ForoChat("María", "Aporto mi opinión aunque quizá no sea muy popular."),
        ForoChat("Toni", "¿Alguien más está siguiendo este debate?"),
        ForoChat("Elena", "He votado en la encuesta, a ver los resultados."),
        ForoChat("Ferran", "Buena idea abrir este hilo, hacía falta."),
        ForoChat("Julia", "No estoy del todo de acuerdo, pero lo respeto."),
        ForoChat("Pablo", "¿Alguna recomendación sobre lo que comentáis?"),
        ForoChat("Irene", "Me encanta cómo se está desarrollando esta conversación."),
        ForoChat("Óscar", "Dejo mi comentario y sigo leyendo."),
        ForoChat("Clara", "¿Alguien podría explicar un poco más el contexto?"),
        ForoChat("Hugo", "¡Buen hilo! Me quedo por aquí.")
    )
}