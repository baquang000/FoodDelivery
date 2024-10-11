package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateUserInfor
import com.example.fooddelivery.data.model.ResultState
import com.example.fooddelivery.data.model.UserInfor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPIService {
    @GET("/api/v1/userinfor/{idUser}")
    suspend fun getUser(@Path("idUser") idUser: Int): UserInfor

    @POST("/api/v1/userinfor")
    suspend fun createUser(@Body userInfor: CreateUserInfor): Response<ResultState>

    @PUT("/api/v1/userinfor/{idUser}")
    suspend fun updateUser(
        @Path("idUser") idUser: Int,
        @Body userInfor: UserInfor
    ): Response<UserInfor>

}