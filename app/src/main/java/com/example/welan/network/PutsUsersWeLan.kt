package com.example.welan.network

import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserWeLanPutApiService {

    @PUT("commonUser/actualizar/{email}/{password}")
    suspend fun putCommonUserpassword(
        @Path("email") email: String,
        @Path("password") password: String
    ): Response<CommonUserWeLan>

    @PUT("commonUser/actualizar/{email}")
    suspend fun putCommonUser(
        @Path("email") email: String,
        @Body user: CommonUserWeLan
    ): Response<CommonUserWeLan>

    @PUT("businessUser/actualizar/{email}")
    suspend fun putBusinessUser(
        @Path("email") email: String,
        @Body user: BusinessUserWeLan
    ): Response<BusinessUserWeLan>
}