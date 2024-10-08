package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateFood
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.ResultState
import com.example.fooddelivery.data.model.UpdateBestFood
import com.example.fooddelivery.data.model.UpdateDescriptionFood
import com.example.fooddelivery.data.model.UpdateImageFood
import com.example.fooddelivery.data.model.UpdatePriceFood
import com.example.fooddelivery.data.model.UpdateShowFood
import com.example.fooddelivery.data.model.UpdateTitleFood
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FoodAPIService {
    @GET("/api/v1/foods/getBest")
    suspend fun getBestFood(): List<Food>

    @GET("/api/v1/foods")
    suspend fun getAllFood(): List<Food>

    //@Header("Authorization") token: String
    @Headers("accept: application/json")
    @GET("/api/v1/foods/category/{categoryId}")
    suspend fun getCategory(@Path("categoryId") id: Int): List<Food>

    @POST("/api/v1/foods")
    suspend fun createFood(
        @Body createFood: CreateFood
    ): Response<ResultState>

    @PUT("/api/v1/foods/showFood/{idShop}")
    suspend fun updateShowFood(
        @Path("idShop") idShop: Int,
        @Body showFood: UpdateShowFood
    ): Response<ResultState>

    @PUT("/api/v1/foods/bestFood/{idShop}")
    suspend fun updateBestFood(
        @Path("idShop") idShop: Int,
        @Body bestFood: UpdateBestFood
    ): Response<ResultState>

    @PUT("/api/v1/foods/title/{idShop}")
    suspend fun updateTitleFood(
        @Path("idShop") idShop: Int,
        @Body bestFood: UpdateTitleFood
    ): Response<ResultState>


    @PUT("/api/v1/foods/price/{idShop}")
    suspend fun updatePriceFood(
        @Path("idShop") idShop: Int,
        @Body bestFood: UpdatePriceFood
    ): Response<ResultState>


    @PUT("/api/v1/foods/description/{idShop}")
    suspend fun updateDescriptionFood(
        @Path("idShop") idShop: Int,
        @Body bestFood: UpdateDescriptionFood
    ): Response<ResultState>


    @PUT("/api/v1/foods/image/{idShop}")
    suspend fun updateImageFood(
        @Path("idShop") idShop: Int,
        @Body bestFood: UpdateImageFood
    ): Response<ResultState>

    @GET("/api/v1/foods/shop/{id}")
    suspend fun getFoodByShop(@Path("id") id: Int): List<Food>
}