package com.example.welan.network

import com.example.welan.model.ForoDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForoWeLanPostApiService {

    @POST("newForo")
    suspend fun postForo(
        @Body foro: ForoDTO
    ): Response<Unit>
}