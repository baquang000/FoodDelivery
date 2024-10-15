package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.ChartCount
import com.example.fooddelivery.data.model.ChartOrder
import retrofit2.http.GET
import retrofit2.http.Path

interface ChartsAPIService {
    @GET("/api/v1/charts/shop/count/{id}")
    suspend fun getChartsByShop(@Path("id") id: Int): ChartCount

    @GET("/api/v1/charts/shop/order-stat/{id}")
    suspend fun getChartOrderByShop(@Path("id") id: Int): ChartOrder

    @GET("/api/v1/charts/shop/revenue/{id}")
    suspend fun getChartRevenueByShop(@Path("id") id: Int): ChartOrder
}