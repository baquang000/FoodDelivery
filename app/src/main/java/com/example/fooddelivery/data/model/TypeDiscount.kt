package com.example.fooddelivery.data.model

enum class TypeDiscount {
    PERCENTAGE,
    FIXED;
    override fun toString(): String {
        return when (this) {
            PERCENTAGE -> "PERCENTAGE"
            FIXED -> "FIXED"
        }
    }
}