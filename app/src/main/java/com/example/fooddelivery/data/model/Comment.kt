package com.example.fooddelivery.data.model

data class CreateComment(
    val idShop: String,
    val idUser: String,
    val idFood: Int?,
    val idOrder: String,
    val content: String,
    val imagePath: String?,
    val rating: Double,
    val time: String
)


data class GetComment(
    val content: String,
    val id: String,
    val idFood: Int?,
    val idOrder: String,
    val idShop: String,
    val idUser: String,
    val imagePath: String?,
    val rating: String,
    val time: String,
    val user: UserInfor
)
