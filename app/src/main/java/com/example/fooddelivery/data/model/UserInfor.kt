package com.example.fooddelivery.data.model

data class UserInfor(
    val address: String?,
    val dateOfBirth: String?,
    val email: String,
    val id: Int,
    val name: String?,
    val numberPhone: String?,
    val idAccount: Int
)

data class CreateUserInfor(
    val address: String?,
    val dateOfBirth: String?,
    val email: String,
    val name: String?,
    val numberPhone: String?,
    val idAccount: Int
)