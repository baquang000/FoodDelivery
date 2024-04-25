package com.example.fooddelivery.data.model

sealed class FoodState {
    class Success(val data: MutableList<Food>) : FoodState()
    class Failure(val message: String) : FoodState()
    object Loading : FoodState()
    object Empty : FoodState()
}