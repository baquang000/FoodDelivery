package com.example.fooddelivery.data.model

//get Food
data class Food(
    val bestFood: Boolean,
    val createdAt: String,
    val deletedAt: String?,
    val description: String,
    val id: Int,
    val idCategory: Int,
    val idPrice: Int,
    val idShop: Int,
    val idTime: Int,
    val imagePath: String,
    val price: Price,
    val showFood: Boolean,
    val sold: Int,
    val star: String,
    val time: Time,
    val title: String,
    val updatedAt: String?
)


data class FoodItem(
    val bestFood: Boolean,
    val createdAt: String,
    val deletedAt: Any,
    val description: String,
    val id: Int,
    val idCategory: Int,
    val idPrice: Int,
    val idShop: Int,
    val idTime: Int,
    val imagePath: String,
    val price: Any,
    val showFood: Boolean,
    val sold: Int,
    val star: String,
    val time: Time,
    val title: String,
    val updatedAt: String
)


//////////////create
data class CreateFood(
    val bestFood: Boolean,
    val categoryId: Int,
    val description: String,
    val idShop: Int,
    val imagePath: String,
    val price: String,
    val showFood: Boolean,
    val star: String,
    val timeValue: String,
    val title: String
)

//////////update
data class UpdateShowFood(
    val id: Int,
    val showFood: Boolean
)

data class UpdateBestFood(
    val id: Int,
    val bestFood: Boolean
)

data class UpdateTitleFood(
    val id: Int,
    val title: String
)

data class UpdatePriceFood(
    val id: Int,
    val price: Double
)

data class UpdateDescriptionFood(
    val id: Int,
    val description: String
)

data class UpdateImageFood(
    val id: Int,
    val imagePath: String
)
