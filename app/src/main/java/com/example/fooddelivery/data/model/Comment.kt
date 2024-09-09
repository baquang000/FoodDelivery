package com.example.fooddelivery.data.model

data class Comment(
    val idFood: Int? = null,
    val comment: String = "",
    val rating: Float = 0f,
    var time: String = "",
    var imageUrl: String = "",
    var nameUser: String = "",
    var idShop: String = "",
    var idUser: String? = ""
)
