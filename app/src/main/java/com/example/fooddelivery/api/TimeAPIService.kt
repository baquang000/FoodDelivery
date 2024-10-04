package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.Time
import retrofit2.http.GET

interface TimeAPIService {
    @GET("/api/v1/time")
    suspend fun getTime(): List<Time>
}