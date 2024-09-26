package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.model.GetFoodDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderDetailAPIService {
    @POST("/order-details/{idOrder}")
    suspend fun createOrderDetail(
        @Path("idOrder") idOrder: String,
        @Body order: FoodDetails
    ): Response<FoodDetails>

    @GET("/order-details/{idOrder}")
    suspend fun getOrderDetails(@Path("idOrder") idOrder: String): List<GetFoodDetail>
}