package com.example.welan.network

import com.example.welan.model.MensajesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MensajesWelanGetApiService {
    @GET("/conversation/{email}")
    suspend fun getMensajes(
        @Path("email") email: String
    ): Response<List<MensajesData>>

    @GET("/conversationExists")
    suspend fun validateConversacion(
        @Query("emailOr") emailOr: String,
        @Query("emailDes") emailDes: String,
    ):  Response<Boolean>
    @GET("/userExists")
    suspend fun validateUser(
        @Query("email") email: String,
    ): Response<Boolean>

}