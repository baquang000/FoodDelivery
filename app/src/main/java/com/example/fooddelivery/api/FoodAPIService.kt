package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.Food
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface FoodAPIService {
    @GET("/api/v1/foods/getBest")
    suspend fun getBestFood(): List<Food>

    @GET("/api/v1/foods")
    suspend fun getAllFood(@Header("Authorization") token: String): List<Food>

    @Headers("accept: application/json")
    @GET("/api/v1/foods/category/{categoryId}")
    suspend fun getCategory(@Path("categoryId") id: Int): List<Food>

}