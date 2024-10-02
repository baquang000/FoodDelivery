package com.example.fooddelivery.data.model

data class Food(
    val bestFood: Boolean,
    val categoryId: Int,
    val description: String,
    val idFood: Int,
    val idShop: String,
    val imagePath: String,
    val locationId: Int,
    val price: Int,
    val priceId: Int,
    val showFood: Boolean,
    val star: Double,
    val timeId: Int,
    val timeValue: Int,
    val title: String
)

data class CreateFood(
    val bestFood: Boolean,
    val categoryId: Int,
    val description: String,
    val idShop: String,
    val imagePath: String,
    val locationId: Int,
    val price: Double,
    val priceId: Int,
    val showFood: Boolean,
    val star: Double,
    val timeId: Int,
    val timeValue: Int,
    val title: String
)


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
