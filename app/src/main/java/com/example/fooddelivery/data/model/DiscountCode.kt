package com.example.fooddelivery.data.model
//get
data class GetDiscountItem(
    val createdAt: String?,
    val deletedAt: String?,
    val description: String,
    val endDate: String,
    val id: Int,
    val idShop: Int,
    val isActive: Boolean,
    val maxDiscountAmount: String,
    val maxUser: Int,
    val minOrderAmount: String,
    val name: String,
    val numberUse: Int,
    val percentage: String,
    val startDate: String,
    val typeDiscount: String,
    val updatedAt: String
)

//create
data class CreateDiscount(
    val description: String,
    val endDate: String,
    val idShop: Int,
    val isActive: Boolean,
    val maxDiscountAmount: String,
    val maxUser: Int,
    val minOrderAmount: String,
    val name: String,
    val numberUse: Int,
    val percentage: String,
    val startDate: String,
    val typeDiscount: String
)