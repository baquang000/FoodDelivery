package com.example.fooddelivery.data.model

data class Auth(
    val email: String,
    val password: String,
)


data class RegisterResult(
    val data: DataRegister,
    val message: String,
    val statusCode: Int
)

data class DataRegister(
    val email: String,
    val id: String,
    val role: String
)