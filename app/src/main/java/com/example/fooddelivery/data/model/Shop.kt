package com.example.fooddelivery.data.model



/////////Get shop
data class GetShopItem(
    val address: String,
    val email: String,
    val foods: List<Food>,
    val idShop: String,
    val imageUrl: String,
    val name: String,
    val orders: List<Order>,
    val phoneNumber: String,
    val starShop: String,
    val titleShop: String
)

data class Order(
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val idOrder: String,
    val idShop: String,
    val idUser: String,
    val noteOrder: String,
    val orderStatus: String,
    val rewardForDriver: Int,
    val sumPrice: Int,
    val time: String
)

data class Shop(
    val address: String,
    val email: String,
    val idShop: String,
    val imageUrl: String,
    val name: String,
    val phoneNumber: String,
    val starShop: Double,
    val titleShop: String
)


data class UpdateShopInfor(
    val address: String,
    val email: String,
    val imageUrl: String,
    val name: String,
    val phoneNumber: String,
    val titleShop: String
)
