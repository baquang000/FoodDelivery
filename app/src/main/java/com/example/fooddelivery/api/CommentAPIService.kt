package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateComment
import com.example.fooddelivery.data.model.GetComment
import com.example.fooddelivery.data.model.ResultState
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentAPIService {
    @POST("/comment")
    suspend fun createComment(
        @Body comment: CreateComment
    ): Response<ResultState>

    @GET("/comment/shop/{idShop}")
    suspend fun getCommentByShop(@Path("idShop") idShop: String): List<GetComment>
}