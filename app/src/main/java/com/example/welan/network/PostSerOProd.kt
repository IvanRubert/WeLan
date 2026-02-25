package com.example.welan.network

import com.example.welan.model.ProductoOServicio
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SerOProdWeLanPostApiService {

    @POST("/newServicioProducto")
    suspend fun postSerOProd(
        @Body serOProd: ProductoOServicio
    ): Response<Unit>
}