package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.newFood
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FoodAPIService {
    @GET("/foods/getBest")
    suspend fun getBestFood(): List<newFood>

    @GET("/foods")
    suspend fun getAllFood(): List<newFood>

    @Headers("accept: application/json")
    @GET("/foods/category/{categoryId}")
    suspend fun getCategory(@Path("categoryId") id: Int): List<newFood>

}