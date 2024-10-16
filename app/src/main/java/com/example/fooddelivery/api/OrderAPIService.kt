package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateOrder
import com.example.fooddelivery.data.model.GetOrderItem
import com.example.fooddelivery.data.model.ResultState
import com.example.fooddelivery.data.model.TResult
import com.example.fooddelivery.data.model.UpdateOrder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderAPIService {
    @POST("/api/v1/order/")
    suspend fun createOrder(
        @Header("Authorization") token:String,
        @Body order: CreateOrder,
    ): Response<ResultState>

    @GET("/api/v1/order/{idOrder}")
    suspend fun getOrderByUser(@Path("idOrder") idOrder: Int): List<GetOrderItem>


    @GET("/api/v1/order/shop/{idShop}")
    suspend fun getOrderByShop(@Path("idShop") idShop: Int): List<GetOrderItem>

    @PUT("/api/v1/order/{id}")
    suspend fun updateOrder(
        @Header("Authorization") token:String,
        @Path("id") id: Int,
        @Body statusOrder: UpdateOrder
    ): Response<TResult>

}