package com.example.fooddelivery.data.model


data class GetFavorite(
    val id: Int,
    val idShop: String,
    val idUser: String,
    val shop: Shop
)

data class CreateFavorite(
    val idShop: String,
    val idUser: String,
)


