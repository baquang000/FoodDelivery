package com.example.fooddelivery.data.model


/////////Get shop

data class GetShopItem(
    val address: String,
    val createdAt: String,
    val deletedAt: String?,
    val email: String,
    val foods: List<Food>,
    val id: Int,
    val idAccount: Int,
    val imageUrl: String,
    val name: String,
    val orders: List<Order>,
    val phoneNumber: String,
    val starShop: String,
    val titleShop: String,
    val updatedAt: String?
)

data class Order(
    val createdAt: String,
    val deletedAt: String?,
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val id: Int,
    val idShop: Int,
    val idUser: Int,
    val noteOrder: String,
    val orderStatus: String,
    val rewardForDriver: Int,
    val totalMoney: String,
    val updatedAt: String?
)


////////////update
data class UpdateShopInfor(
    val address: String,
    val email: String,
    val imageUrl: String,
    val name: String,
    val phoneNumber: String,
    val titleShop: String
)


///create
data class CreateShop(
    val address: String,
    val email: String,
    val imageUrl: String,
    val name: String,
    val phoneNumber: String,
    val starShop: String,
    val titleShop: String,
    val idAccount: Int
)
