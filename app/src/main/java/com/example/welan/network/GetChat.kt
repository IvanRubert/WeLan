package com.example.welan.network

import com.example.welan.model.chatDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetChatsWelanApiService {
    @GET("/conversation/message/{email}/{email2}")
    suspend fun getConversation(
        @Path("email") email: String,
        @Path("email2") email2 : String
    ): Response<List<chatDTO>>

}