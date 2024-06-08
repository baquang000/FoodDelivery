package com.example.fooddelivery.data.model

sealed class DiscountCodeState {
    class Success(val data: MutableList<DiscountCode>) : DiscountCodeState()
    class Failure(val message: String) : DiscountCodeState()
    data object Loading : DiscountCodeState()
    data object Empty : DiscountCodeState()
}
