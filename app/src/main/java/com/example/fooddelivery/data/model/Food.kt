package com.example.fooddelivery.data.model

data class Food(
    var BestFood: Boolean = false,
    var CategoryId: Int = 0,
    var Description: String? = null,
    var Id: Int = 0,
    var ImagePath: String? = null,
    var LocationId: Int = 0,
    var Price: Double = 0.0,
    var PriceId: Int = 0,
    var Star: Double = 0.0,
    var TimeId: Int = 0,
    var TimeValue: Int = 0,
    var Title: String? = null,
    var show: Boolean = true
)
