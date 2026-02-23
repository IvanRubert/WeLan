package com.example.welan.network

import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeLanGetApiService {
    @GET("commonUser/{email}/{password}")
    suspend fun getCommonUserValidate(
        @Path("email") email: String,
        @Path("password") password: String
    ): Response<CommonUserWeLan>

    @GET("commonUser/validated/{email}")
    suspend fun getCommonUser(
        @Path("email") email: String,
    ): Response<CommonUserWeLan>

    @GET("businessUser/{email}/{organizacion}")
    suspend fun getBusinessUserValidate(
        @Path("email") email: String,
        @Path("organizacion") organizacion: String
    ): Response<BusinessUserWeLan>

    @GET("businessUser/validated/{email}")
    suspend fun getBusinessUser(
        @Path("email") email: String,
    ): Response<BusinessUserWeLan>
}