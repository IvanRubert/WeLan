package com.example.welan.network

import com.example.welan.model.Foro
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ForoWeLanGetApiService {
    @GET("/Foros")
    suspend fun getForos(): Response<List<Foro>>

    @GET("/Foros/{titulo}")
    suspend fun getForosFiltro(
        @Path("titulo") titulo: String,
    ): Response<List<Foro>>
}