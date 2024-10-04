package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.Price
import retrofit2.http.GET

interface PriceAPIService {
    @GET("/api/v1/price")
    suspend fun getPrice(): List<Price>
}