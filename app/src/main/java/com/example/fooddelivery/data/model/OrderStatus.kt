package com.example.fooddelivery.data.model

enum class OrderStatus {
    PENDING,
    DELIVERY,
    SUCCESS,
    CANCEL,
    FOODBACK;

    override fun toString(): String {
        return when (this) {
            PENDING -> "PENDING"
            DELIVERY -> "DELIVERY"
            SUCCESS -> "SUCCESS"
            CANCEL -> "CANCEL"
            FOODBACK -> "FOODBACK"
        }
    }
}
