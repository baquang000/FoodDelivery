package com.example.fooddelivery.data.model

sealed class PriceState {
    class Success(val data: MutableList<Price>) : PriceState()
    class Failure(val message: String) : PriceState()
    data object Loading : PriceState()
    data object Empty : PriceState()
}