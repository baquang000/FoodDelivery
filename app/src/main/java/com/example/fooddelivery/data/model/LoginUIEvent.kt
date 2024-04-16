package com.example.fooddelivery.data.model

sealed class LoginUIEvent {
    data class EmailChange(val email: String) : LoginUIEvent()
    data class PasswordChange(val password: String) : LoginUIEvent()

    data object LoginButtonClicked : LoginUIEvent()
}