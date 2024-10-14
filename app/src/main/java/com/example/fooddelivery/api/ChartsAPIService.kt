package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.ChartCount
import retrofit2.http.GET
import retrofit2.http.Path

interface ChartsAPIService {
    @GET("/api/v1/charts/shop/count/{id}")
    suspend fun getChartsByShop(@Path("id") id: Int): ChartCount

}