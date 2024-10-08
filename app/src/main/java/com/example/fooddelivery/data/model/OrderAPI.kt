package com.example.fooddelivery.data.model

/*  get order from api */

data class GetOrderItem(
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val id: Int,
    val idShop: Int,
    val idUser: Int,
    val noteOrder: String,
    val orderDetails: List<OrderDetail>,
    val orderStatus: String,
    val rewardForDriver: Int,
    val sumPrice: Double,
    val time: String,
    val user: User
)


data class OrderDetail(
    val id: String,
    val idFood: Int,
    val idOrder: Int,
    val imagePath: String,
    val price: Double,
    val quantity: Int,
    val title: String
)

data class User(
    val address: String,
    val dateOfBirth: String,
    val email: String,
    val id: Int,
    val name: String,
    val numberPhone: String
)

///update
data class UpdateOrder(
    val orderStatus: String,
    val time: String
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
    val sumPrice: Double,
    val time: String
)


