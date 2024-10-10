package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateDiscount
import com.example.fooddelivery.data.model.GetDiscountItem
import com.example.fooddelivery.data.model.ResultState
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscountAPIService {
    @GET("/api/v1/discount/{id}")
    suspend fun getByShop(@Path("id") idShop: Int): List<GetDiscountItem>

    @GET("/api/v1/discount/single/{id}")
    suspend fun getSingle(@Path("id") id: Int): GetDiscountItem

    @POST("/api/v1/discount")
    suspend fun createDiscount(@Body discount: CreateDiscount) : Response<ResultState>
}