package com.example.fooddelivery.data.model

data class newShop(
    val address: String,
    val email: String,
    val foods: List<newFood>,
    val idShop: String,
    val imageUrl: String,
    val name: String,
    val phoneNumber: String,
    val starShop: Double,
    val titleShop: String
)
