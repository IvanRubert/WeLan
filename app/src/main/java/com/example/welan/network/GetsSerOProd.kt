package com.example.welan.network

import com.example.welan.model.ProductoOServicio
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SerOProdWeLanGetApiService {
    @GET("getservicios")
    suspend fun getSerOProd(): Response<List<ProductoOServicio>>

    @GET("getservicios/{nombre}")
    suspend fun getSerOProdFiltro(
        @Path("nombre") nombre: String,
    ): Response<List<ProductoOServicio>>
}