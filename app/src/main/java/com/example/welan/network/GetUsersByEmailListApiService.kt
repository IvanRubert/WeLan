package com.example.welan.network

import com.example.welan.model.UserWelan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetUsersByEmailListApiService {
    @POST("/getUsersByCorreoList")
    suspend fun getUsersByEmailList(
        @Body emailList: List<String>,
    ): Response<List<UserWelan>>
}