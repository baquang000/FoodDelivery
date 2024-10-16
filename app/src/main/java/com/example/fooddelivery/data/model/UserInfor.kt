package com.example.fooddelivery.data.model

data class UserInfor(
    val address: String?,
    val createdAt: String,
    val dateOfBirth: String?,
    val deletedAt: String?,
    val email: String,
    val id: Int,
    val name: String?,
    val numberPhone: String?,
    val idAccount: Int,
    val updatedAt: String
)

data class CreateUserInfor(
    val address: String?,
    val dateOfBirth: String?,
    val email: String,
    val name: String?,
    val numberPhone: String?,
    val idAccount: Int
)

data class GetUser(
    val address: String,
    val createdAt: String,
    val dateOfBirth: String,
    val deletedAt: String?,
    val email: String,
    val id: Int,
    val idAccount: Int,
    val name: String,
    val numberPhone: String,
    val updatedAt: String
)