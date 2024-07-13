package com.example.fooddelivery.data.model

sealed class SignupUIEvent {
    data class emailChange(val email: String) : SignupUIEvent()
    data class PasswordChange(val password: String) : SignupUIEvent()
    data class CurPasswordChange(val curpassword: String) : SignupUIEvent()
    data object RegisterButtonClicked : SignupUIEvent()
}