package com.example.fooddelivery.data.model

data class DiscountCode(
    val id: Int = 0,
    val name: String = "",
    val value: Float = 0f,
    val isshow: Boolean = false
)

data class GetDiscountItem(
    val code: String,
    val createdAt: String,
    val description: String,
    val endDate: String,
    val id: Int,
    val idShop: String,
    val isActive: Boolean,
    val maxDiscountAmount: String,
    val minOrderAmount: String,
    val name: String,
    val percentage: String,
    val startDate: String,
    val updatedAt: String,
    val typeDiscount: TypeDiscount
)
