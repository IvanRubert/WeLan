package com.example.welan.data

import com.example.welan.network.NoticiasWeLanGetApiService
import com.example.welan.network.SerOProdWeLanGetApiService
import com.example.welan.network.SerOProdWeLanPostApiService
import com.example.welan.network.UserWeLanGetApiService
import com.example.welan.network.UserWeLanPostApiService
import com.example.welan.network.UserWeLanPutApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val weLanRepository: WeLanRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "http://wikifilmapi.duckdns.org/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitgetMessage: MensajesWelanApiService by lazy {
        retrofit.create(MensajesWelanApiService::class.java)
    }
    private val retrofitgetUserService: UserWeLanGetApiService by lazy {
        retrofit.create(UserWeLanGetApiService::class.java)
    }

    private val retrofitpostUserService: UserWeLanPostApiService by lazy {
        retrofit.create(UserWeLanPostApiService::class.java)
    }
    private val retrofitputUserService: UserWeLanPutApiService by lazy {
        retrofit.create(UserWeLanPutApiService::class.java)
    }

    private val retrofitgetSerOProdService: SerOProdWeLanGetApiService by lazy {
        retrofit.create(SerOProdWeLanGetApiService::class.java)
    }

    private val retrofitPostSerOProdService: SerOProdWeLanPostApiService by lazy {
        retrofit.create(SerOProdWeLanPostApiService::class.java)
    }

    private val retrofitgetNoticiaService: NoticiasWeLanGetApiService by lazy {
        retrofit.create(NoticiasWeLanGetApiService::class.java)
    }

    override val weLanRepository by lazy {
        NetworkWeLanRepository(
            retrofitgetUserService,
            retrofitpostUserService,
            retrofitputUserService,
            retrofitgetSerOProdService,
            retrofitPostSerOProdService,
            retrofitgetNoticiaService
        )
    }
}