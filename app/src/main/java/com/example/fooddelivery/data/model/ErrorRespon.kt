package com.example.fooddelivery.data.model

data class ErrorResponse(
    val error: String,
    val message: String,
    val statusCode: Int
)