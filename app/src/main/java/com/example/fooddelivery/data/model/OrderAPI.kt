package com.example.fooddelivery.data.model

/*  get order from api */

data class GetOrderItem(
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val idOrder: String,
    val idShop: String,
    val idUser: String,
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
    val idOrder: String,
    val imagePath: String,
    val price: Double,
    val quantity: Int,
    val title: String
)

data class User(
    val address: String,
    val dateOfBirth: String,
    val email: String,
    val idUser: String,
    val name: String,
    val numberPhone: String
)






///update

data class UpdateOrder(
    val statusOrder: String
)


////create order


data class CreateOrder(
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val idShop: String,
    val idUser: String,
    val noteOrder: String,
    val orderDetails: List<FoodDetails>,
    val rewardForDriver: Int,
    val sumPrice: Double,
    val time: String
)


