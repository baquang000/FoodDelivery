package com.example.fooddelivery.data.model

///////// lấy order từ api
data class GetOrder(
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val idOrder: String,
    val idShop: String,
    val idUser: String,
    val noteOrder: String,
    val orderDetails: List<OrderDetail>,
    val orderStatus: String,
    val rewardForDriver: Int,
    val sumPrice: Int,
    val time: String
)

data class OrderDetail(
    val id: Int,
    val idFood: Int,
    val idOrder: String,
    val quantity: Int
)


/////// tạo các order từ giao diện người dùng
data class CreateOrder(
    val deliverytoDoor: Boolean,
    val diningSubtances: Boolean,
    val idShop: String,
    val idUser: String,
    val noteOrder: Any,
    val rewardForDriver: Int,
    val sumPrice: Double,
    val time: String
)

