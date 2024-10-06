package com.example.fooddelivery.data.model


data class LoginResutl(
    val data: DataLogin,
    val message: String,
    val statusCode: Int
)

data class DataLogin(
    val accessToken: String,
    val rest: Rest
)

data class Rest(
    val email: String,
    val id: String
)


data class changePassword(
    val id:String,
    val currentPassword:String,
    val newPassword:String
)