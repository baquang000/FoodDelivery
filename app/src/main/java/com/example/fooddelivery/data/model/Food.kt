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





//////////////create
data class CreateFood(
    val bestFood: Boolean,
    val idCategory: Int,
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
    val idFood: Int,
    val showFood: Boolean
)

data class UpdateBestFood(
    val idFood: Int,
    val bestFood: Boolean
)

data class UpdateTitleFood(
    val idFood: Int,
    val title: String
)

data class UpdatePriceFood(
    val idFood: Int,
    val price: Double
)

data class UpdateDescriptionFood(
    val idFood: Int,
    val description: String
)

data class UpdateImageFood(
    val idFood: Int,
    val imagePath: String
)
