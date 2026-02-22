package com.example.welan.network

import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeLanGetApiService {
    @GET("commonuservalidate/{email}/{password}")
    suspend fun getCommonUserValidate(
        @Path("email") email: String,
        @Path("password") password: String
    ): Response<CommonUserWeLan>

    @GET("commonuser/{email}")
    suspend fun getCommonUser(
        @Path("email") email: String,
    ): Response<CommonUserWeLan>

    @GET("businessuservalidate/{email}/{organizacion}")
    suspend fun getBusinessUserValidate(
        @Path("email") email: String,
        @Path("organizacion") organizacion: String
    ): Response<BusinessUserWeLan>

    @GET("businessuser/{email}")
    suspend fun getBusinessUser(
        @Path("email") email: String,
    ): Response<BusinessUserWeLan>
}