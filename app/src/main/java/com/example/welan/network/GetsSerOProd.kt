package com.example.welan.network

import com.example.welan.model.ProductoOServicio
import retrofit2.Response
import retrofit2.http.GET

interface SerOProdWeLanGetApiService {
    @GET("getservicios")
    suspend fun getSerOProd(): Response<List<ProductoOServicio>>
}