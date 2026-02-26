package com.example.welan.network

import com.example.welan.model.SendForoChatDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostForoChatApiService {
    @POST("/foro/newMessage")
    suspend fun postChat(
        @Body chat: SendForoChatDTO,
    ): Response<Unit>
}