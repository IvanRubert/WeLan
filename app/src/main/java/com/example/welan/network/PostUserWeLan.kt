package com.example.welan.network

import com.example.welan.model.CreateBusinessUserDTO
import com.example.welan.model.CreateCommonUserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserWeLanPostApiService {

    @POST("newCommonUser")
    suspend fun postCommonUser(
        @Body user: CreateCommonUserDTO
    ): Response<Unit>

    @POST("newBusinessUser")
    suspend fun postBusinessUser(
        @Body user: CreateBusinessUserDTO
    ): Response <Unit>
}