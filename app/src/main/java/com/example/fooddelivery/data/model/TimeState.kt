package com.example.fooddelivery.data.model

sealed class TimeState {
    class Success(val data: MutableList<Time>) : TimeState()
    class Failure(val message: String) : TimeState()
    data object Loading : TimeState()
    data object Empty : TimeState()
}