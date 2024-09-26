package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.newShop
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopAPIService {
    @GET("/shop/{idShop}")
    suspend fun getInforShop(@Path("idShop") idShop: String): List<newShop>
}