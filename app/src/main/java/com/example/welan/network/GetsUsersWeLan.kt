package com.example.welan.network

import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserWeLanGetApiService {
    @GET("/commonUser")
    suspend fun getCommonUserValidate(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<CommonUserWeLan>

    @GET("/commonUser/validated/{email}")
    suspend fun getCommonUser(
        @Path("email") email: String,
    ): Response<CommonUserWeLan>

    @GET("/businessUser")
    suspend fun getBusinessUserValidate(
        @Query("email") email: String,
        @Query("certificate") certificate: String
    ): Response<BusinessUserWeLan>

    @GET("/businessUser/validated/{email}")
    suspend fun getBusinessUser(
        @Path("email") email: String,
    ): Response<BusinessUserWeLan>
}