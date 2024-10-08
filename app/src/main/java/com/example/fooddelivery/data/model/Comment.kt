package com.example.fooddelivery.data.model

data class CreateComment(
    val idShop: Int,
    val idUser: Int,
    val idFood: Int?,
    val idOrder: Int,
    val content: String,
    val imagePath: String?,
    val rating: Double,
    val time: String
)


data class GetComment(
    val content: String,
    val id: String,
    val idFood: Int?,
    val idOrder: Int,
    val idShop: Int,
    val idUser: Int,
    val imagePath: String?,
    val rating: String,
    val time: String,
    val user: UserInfor
)
