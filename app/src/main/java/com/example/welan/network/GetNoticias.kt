package com.example.welan.network

import com.example.welan.model.NoticiDTO
import retrofit2.Response
import retrofit2.http.GET

interface NoticiasWeLanGetApiService {
    @GET("/noticias")
    suspend fun getNoticias(): Response<List<NoticiDTO>>
}