package com.example.fooddelivery.data.model

data class FoodDetails(
    var title: String? = null,
    var imagePath: String? = null,
    var price: Float = 0f,
    var quantity: Int = 0,
    var idFood: Int? = null,
)


data class GetFoodDetail(
    val id: Int,
    val idFood: Int,
    val idOrder: String,
    val imagePath: String,
    val price: Int,
    val quantity: Int,
    val title: String
)

