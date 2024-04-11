package com.example.fooddelivery.model

sealed class UIEvent {
    data class SdtChange(val sdt: String) : UIEvent()
    data class PasswordChange(val password: String) : UIEvent()

    data object RegisterButtonClicked : UIEvent()
}