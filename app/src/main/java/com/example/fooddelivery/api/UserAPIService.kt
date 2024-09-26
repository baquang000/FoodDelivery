package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.UserInfor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPIService {
    @GET("/userinfor/{idUser}")
    suspend fun getUser(@Path("idUser") idUser: String): UserInfor

    @POST("/userinfor")
    suspend fun createUser(@Body userInfor: UserInfor): Response<UserInfor>

    @PUT("/userinfor/{idUser}")
    suspend fun updateUser(
        @Path("idUser") idUser: String,
        @Body userInfor: UserInfor
    ): Response<UserInfor>
}