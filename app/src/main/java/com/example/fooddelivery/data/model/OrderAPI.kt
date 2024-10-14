package com.example.fooddelivery.data.model

/*  get order from api */

data class GetOrderItem(
    val createdAt: String,
    val deletedAt: String?,
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val id: Int,
    val idShop: Int,
    val idUser: Int,
    val idDiscount: Int,
    val noteOrder: String,
    val orderDetails: List<OrderDetail>,
    val orderStatus: String,
    val rewardForDriver: Int,
    val totalMoney: String,
    val updatedAt: String?,
    val user: User,
)

data class OrderDetail(
    val createdAt: String,
    val deletedAt: String?,
    val id: Int,
    val idFood: Int,
    val idOrder: Int,
    val imagePath: String,
    val price: String,
    val quantity: Int,
    val title: String,
    val updatedAt: String?
)

data class User(
    val address: String,
    val createdAt: String,
    val dateOfBirth: String,
    val deletedAt: String?,
    val email: String,
    val id: Int,
    val idAccount: Int,
    val name: String,
    val numberPhone: String,
    val updatedAt: String?
)

data class Shop(
    val address: String,
    val createdAt: String,
    val deletedAt: String?,
    val email: String,
    val id: Int,
    val idAccount: Int,
    val imageUrl: String,
    val name: String,
    val phoneNumber: String,
    val starShop: String,
    val titleShop: String,
    val updatedAt: String?
)


///update
data class UpdateOrder(
    val orderStatus: String,
)

////create order
data class CreateOrder(
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val idShop: Int,
    val idUser: Int,
    val noteOrder: String,
    val orderDetails: List<FoodDetails>,
    val rewardForDriver: Int,
    val totalMoney: String,
    val idDiscount: Int
)



