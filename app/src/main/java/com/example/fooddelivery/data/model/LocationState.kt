package com.example.fooddelivery.data.model

sealed class LocationState {
    class Success(val data: MutableList<Location>) : LocationState()
    class Failure(val message: String) : LocationState()
    object Loading : LocationState()
    object Empty : LocationState()
}