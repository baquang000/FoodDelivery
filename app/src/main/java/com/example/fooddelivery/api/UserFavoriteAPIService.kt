package com.example.fooddelivery.api

import com.example.fooddelivery.data.model.CreateFavorite
import com.example.fooddelivery.data.model.GetFavorite
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserFavoriteAPIService {
    @POST("/api/v1/user-favorite/")
    suspend fun createUserFavorite(@Header("Authorization") token:String,@Body userGetFavorite: CreateFavorite): Response<CreateFavorite>

    @GET("/api/v1/user-favorite/{id}")
    suspend fun getUserFavorite(@Path("id") id: Int): List<GetFavorite>

    @DELETE("/api/v1/user-favorite/{idUser}/{idShop}")
    suspend fun deleteUserFavorite(
        @Header("Authorization") token:String,
        @Path("idUser") idUser: Int,
        @Path("idShop") idShop: Int
    ): Response<Void>
}