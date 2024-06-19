package com.example.fooddelivery.data.model

data class OrderFood(
    val listFood: List<FoodDetails> = emptyList(),
    var isConfirm: Boolean = false,
    var isDelivering: Boolean = true,
    var isDelivered: Boolean = false,
    var isCancelled: Boolean = false,
    var sumPrice: Float = 0f
)
