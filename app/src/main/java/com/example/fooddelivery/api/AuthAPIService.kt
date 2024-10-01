package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.Auth
import com.example.fooddelivery.data.model.LoginResutl
import com.example.fooddelivery.data.model.RegisterResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPIService {
    @POST("/api/v1/auth/register")
    suspend fun register(@Body authRequest: Auth): Response<RegisterResult>

    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: Auth): Response<LoginResutl>
}