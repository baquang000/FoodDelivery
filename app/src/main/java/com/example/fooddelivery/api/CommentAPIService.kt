package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateComment
import com.example.fooddelivery.data.model.GetComment
import com.example.fooddelivery.data.model.ResultState
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentAPIService {
    @POST("/api/v1/comment")
    suspend fun createComment(
        @Header("Authorization") token:String,
        @Body comment: CreateComment
    ): Response<ResultState>

    @GET("/api/v1/comment/shop/{idShop}")
    suspend fun getCommentByShop(@Path("idShop") idShop: Int): List<GetComment>
}