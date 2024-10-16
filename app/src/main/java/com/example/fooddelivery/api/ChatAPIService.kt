package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateMessage
import com.example.fooddelivery.data.model.GetUser
import com.example.fooddelivery.data.model.Message
import com.example.fooddelivery.data.model.ResultState
import com.example.fooddelivery.data.model.Shop
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatAPIService {
    @GET("/api/v1/chat/{idUser}/{idShop}")
    suspend fun getMessages(@Path("idUser") idUser: Int, @Path("idShop") idShop: Int): List<Message>

    @POST("/api/v1/chat")
    suspend fun sendMessage(@Body message: CreateMessage): Response<ResultState>

    @GET("/api/v1/chat/shop/history/{idShop}")
    suspend fun getHistoryByShop(@Path("idShop") idShop: Int): List<GetUser>

    @GET("/api/v1/chat/user/history/{idUser}")
    suspend fun getHistoryByUser(@Path("idUser") idUser: Int): List<Shop>
}