package com.example.fooddelivery.data.model

data class Food(
    val bestFood: Boolean,
    val categoryId: Int,
    val description: String,
    val idFood: Int,
    val idShop: String,
    val imagePath: String,
    val locationId: Int,
    val price: Int,
    val priceId: Int,
    val showFood: Boolean,
    val star: Double,
    val timeId: Int,
    val timeValue: Int,
    val title: String
)

