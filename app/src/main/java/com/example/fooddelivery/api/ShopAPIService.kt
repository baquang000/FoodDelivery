package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.GetShopItem
import com.example.fooddelivery.data.model.ResultState
import com.example.fooddelivery.data.model.Shop
import com.example.fooddelivery.data.model.UpdateShopInfor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ShopAPIService {
    @GET("/api/v1/shop/{idShop}")
    suspend fun getShopAndFood(@Path("idShop") idShop: Int): List<GetShopItem>

    @GET("/api/v1/shop/infor/{id}")
    suspend fun getInforOfShop(@Path("id") id: Int): Shop

    @PUT("/api/v1/shop/{id}")
    suspend fun updateInforShop(
        @Path("id") id: Int,
        @Body updateShopInfor: UpdateShopInfor
    ): Response<ResultState>

    @POST("/api/v1/shop")
    suspend fun createShop(
        @Body shop: Shop
    ): Response<ResultState>
}