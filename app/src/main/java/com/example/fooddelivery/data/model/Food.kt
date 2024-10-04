package com.example.fooddelivery.data.model

data class Food(
    val bestFood: Boolean,
    val categoryId: Int,
    val description: String,
    val idFood: Int,
    val idShop: String,
    val imagePath: String,
    val price: Price,
    val priceId: Int,
    val showFood: Boolean,
    val star: String,
    val time: Time,
    val timeId: Int,
    val title: String
)


//////////////create
data class CreateFood(
    val bestFood: Boolean,
    val categoryId: Int,
    val description: String,
    val idShop: String,
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
