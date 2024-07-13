package com.example.fooddelivery.data.model

data class OrderFood(
    var id: String? = null,
    val listFood: List<FoodDetails> = emptyList(),
    var comfirm: Boolean = false,
    var delivered: Boolean = false,
    var cancelled: Boolean = false,
    var sumPrice: Float = 0f,
)
