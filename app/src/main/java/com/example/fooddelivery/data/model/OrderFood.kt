package com.example.fooddelivery.data.model

data class OrderFood(
    var idUser: String? = null,
    val listFood: List<FoodDetails> = emptyList(),
    var comfirm: Boolean = false,
    var delivered: Boolean = false,
    var cancelled: Boolean = false,
    var sumPrice: Float = 0f,
    var name: String = "",
    var numberphone: String = "",
    var address: String = "",
    var email: String = "",
    var dateofbirth: String = "",
    var time: String = "",
    var idOrder: String = "",
    var noteOrder: String = "",
    var rewardForDriver: Int = 0,
    var deliverytoDoor: Boolean = false,
    var diningSubtances: Boolean = true
)
