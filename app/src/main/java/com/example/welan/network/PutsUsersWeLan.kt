package com.example.welan.network

import com.example.welan.model.BusinessUserEdit
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserEdit
import com.example.welan.model.CommonUserWeLan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserWeLanPutApiService {

    @Headers("Content-Type:text/plain")
    @PUT("/editUser/password/{email}")
    suspend fun putCommonUserpassword(
        @Path("email") email: String,
        @Body password: String
    ): Response<CommonUserWeLan>

    @PUT("/editCommonUser")
    suspend fun putCommonUser(
        @Body user: CommonUserEdit
    ): Response<CommonUserWeLan>

    @PUT("editBusinessUser")
    suspend fun putBusinessUser(
        @Body user: BusinessUserEdit
    ): Response<BusinessUserWeLan>
}