package com.example.fooddelivery.data.model

data class Message(
    val id: Int,
    val idUser: Int,
    val idShop: Int,
    val content: String,
    val fromMe:Boolean,
    val createdAt: String,
    val updatedAt: String?,
    val deletedAt: String?
)

data class CreateMessage(
    val idUser: Int,
    val idShop: Int,
    val content: String,
    val fromMe:Boolean
)