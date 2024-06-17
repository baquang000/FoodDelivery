package com.example.fooddelivery.data.model

data class TabItemOrder(
    val title: String,
)

val tabItemOrder = listOf(
    TabItemOrder("Chờ xác nhận"),
    TabItemOrder("Đang giao hàng"),
    TabItemOrder("Đã giao"),
    TabItemOrder("Đã hủy")
)