package com.example.welan.data

import com.example.welan.network.WeLanGetApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val weLanRepository: WeLanRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "http://localhost:8081/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: WeLanGetApiService by lazy {
        retrofit.create(WeLanGetApiService::class.java)
    }

    override val weLanRepository by lazy {
        NetworkWeLanRepository(retrofitService)
    }
}