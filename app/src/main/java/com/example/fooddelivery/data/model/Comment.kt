package com.example.fooddelivery.data.model

data class CreateComment(
    val idShop: Int,
    val idUser: Int,
    val idFood: Int?,
    val idOrder: Int,
    val content: String,
    val imagePath: String?,
    val rating: String,
    val time: String
)


data class GetComment(
    val content: String,
    val createdAt: String,
    val deletedAt: String?,
    val id: Int,
    val idFood: Int,
    val idOrder: Int,
    val idShop: Int,
    val idUser: Int,
    val imagePath: String?,
    val rating: String,
    val updatedAt: String,
    val user: UserInfor
)

