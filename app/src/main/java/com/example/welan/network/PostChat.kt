package com.example.welan.network

import com.example.welan.model.chatDTO
import com.example.welan.model.sendchatDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostChatApiService {
    @POST("/conversation/newMessage")
    suspend fun postChat(
        @Body chat: sendchatDTO,
    ): Response<Unit>
}