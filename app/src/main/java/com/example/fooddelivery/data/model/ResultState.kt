package com.example.fooddelivery.data.model


data class ResultState(
    val statusCode: Int,
    val message: String
)

data class TResult(
    val statusCode: Int,
    val message: String,
    val data: Any
)
