package com.example.fooddelivery.data.model


data class GetFavorite(
    val id: Int,
    val idShop: Int,
    val idUser: Int,
    val shop: ShopInfor
)
data class ShopInfor(
    val address: String,
    val email: String,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val phoneNumber: String,
    val starShop: String,
    val titleShop: String
)
data class CreateFavorite(
    val idShop: Int,
    val idUser: Int,
)


