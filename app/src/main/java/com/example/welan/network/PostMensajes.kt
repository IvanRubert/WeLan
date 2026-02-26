package com.example.welan.network

import com.example.welan.model.MensajesData
import com.example.welan.model.MensajesDataCreateDTO
import com.example.welan.model.sendchatDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PostMensajesWelanApiService {
    @POST("/newConversation")
    suspend fun postMensajes(
        @Body conversacion : MensajesDataCreateDTO
    )

}