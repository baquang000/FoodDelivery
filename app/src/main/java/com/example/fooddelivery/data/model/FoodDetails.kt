package com.example.fooddelivery.data.model

data class FoodDetails(
    var title: String? = null,
    var imagePath: String? = null,
    var price: Float = 0f,
    var quantity: Int = 0,
    var id: Int? = null,
)
