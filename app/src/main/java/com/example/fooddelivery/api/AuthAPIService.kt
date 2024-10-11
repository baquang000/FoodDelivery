package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.Auth
import com.example.fooddelivery.data.model.ForgetPassDto
import com.example.fooddelivery.data.model.LoginResutl
import com.example.fooddelivery.data.model.RegisterResult
import com.example.fooddelivery.data.model.RegisterUuidDto
import com.example.fooddelivery.data.model.ResultState
import com.example.fooddelivery.data.model.TResult
import com.example.fooddelivery.data.model.changePassword
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthAPIService {
    @POST("/api/v1/auth/register")
    suspend fun register(@Body authRequest: Auth): Response<RegisterResult>

    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: Auth): Response<LoginResutl>

    @PUT("/api/v1/auth/change-password")
    suspend fun changePassword(@Body changePassword: changePassword): Response<ResultState>

    @POST("/api/v1/auth/check")
    suspend fun checkUUID(@Body registerUuidDto: RegisterUuidDto): Response<ResultState>

    @POST("/api/v1/auth/forget-pass")
    suspend fun forgetPassword(@Body email: ForgetPassDto): Response<TResult>
}