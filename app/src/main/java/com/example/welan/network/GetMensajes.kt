package com.example.welan.network

import com.example.welan.model.MensajesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
interface MensajesWelanGetApiService {
    @GET("/conversation/{email}")
    suspend fun getMensajes(
        @Path("email") email: String
    ): Response<List<MensajesData>>
}