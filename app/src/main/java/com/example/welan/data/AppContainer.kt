package com.example.welan.data

import com.example.welan.network.SerOProdWeLanGetApiService
import com.example.welan.network.UserWeLanGetApiService
import com.example.welan.network.UserWeLanPutApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val weLanRepository: WeLanRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "http://odinmigordin.hopto.org:8081/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitgetUserService: UserWeLanGetApiService by lazy {
        retrofit.create(UserWeLanGetApiService::class.java)
    }

    private val retrofitUserService: UserWeLanPutApiService by lazy {
        retrofit.create(UserWeLanPutApiService::class.java)
    }

    private val retrofitgetSerOProdService: SerOProdWeLanGetApiService by lazy {
        retrofit.create(SerOProdWeLanGetApiService::class.java)
    }

    override val weLanRepository by lazy {
        NetworkWeLanRepository(retrofitgetUserService, retrofitUserService, retrofitgetSerOProdService)
    }
}