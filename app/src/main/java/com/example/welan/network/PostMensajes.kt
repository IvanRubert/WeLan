package com.example.welan.network

import com.example.welan.model.MensajesDataCreateDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface PostMensajesWelanApiService {
    @POST("/newConversation")
    suspend fun postMensajes(
        @Body conversacion : MensajesDataCreateDTO
    )

}