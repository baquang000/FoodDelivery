package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.GetCategory
import retrofit2.http.GET

interface CategoryAPIService {
    @GET("/api/v1/category")
    suspend fun getCategory() : List<GetCategory>
}