package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateFavorite
import com.example.fooddelivery.data.model.GetFavorite
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserFavoriteAPIService {
    @POST("/user-favorite/")
    suspend fun createUserFavorite(@Body userGetFavorite: CreateFavorite): Response<CreateFavorite>

    @GET("/user-favorite/{id}")
    suspend fun getUserFavorite(@Path("id") id: String): List<GetFavorite>

    @DELETE("/user-favorite/{idUser}/{idShop}")
    suspend fun deleteUserFavorite(
        @Path("idUser") idUser: String,
        @Path("idShop") idShop: String
    ): Response<Void>
}