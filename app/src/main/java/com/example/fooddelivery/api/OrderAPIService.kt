package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateOrder
import com.example.fooddelivery.data.model.GetOrder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderAPIService {
    @POST("/order/{idOrder}")
    suspend fun createOrder(
        @Path("idOrder") idOrder: String,
        @Body order: CreateOrder
    ): Response<CreateOrder>

    @GET("/order/{idOrder}")
    suspend fun getOrderByUser(@Path("idOrder") idOrder: String): List<GetOrder>
}