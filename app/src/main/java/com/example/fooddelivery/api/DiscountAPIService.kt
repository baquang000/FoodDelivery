package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.GetDiscountItem
import retrofit2.http.GET
import retrofit2.http.Path

interface DiscountAPIService {
    @GET("/api/v1/discount/{id}")
    suspend fun getByShop(@Path("id") idShop: String): List<GetDiscountItem>
}