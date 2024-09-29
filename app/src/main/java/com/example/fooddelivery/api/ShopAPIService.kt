package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.GetShopItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopAPIService {
    @GET("/shop/{idShop}")
    suspend fun getInforShop(@Path("idShop") idShop: String): List<GetShopItem>
}