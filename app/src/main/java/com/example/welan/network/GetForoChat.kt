package com.example.welan.network

import com.example.welan.model.ForoChatDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetForoChatsWelanApiService {
    @GET("/Foros/Mensajes/{foroId}")
    suspend fun getConversation(
        @Path("foroId") foroId: Int
    ): Response<List<ForoChatDTO>>

}