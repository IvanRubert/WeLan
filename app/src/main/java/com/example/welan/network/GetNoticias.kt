package com.example.welan.network

import com.example.welan.model.NoticiDTO
import com.example.welan.model.ProductoOServicio
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NoticiasGetApiService {
    @GET("/noticias")
    suspend fun getNoticias(): Response<List<NoticiDTO>>
}