package com.example.fooddelivery.data.model

data class SignUpUIState(
    var email: String = "",
    var password: String = "",
    var curpassword: String = "",
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var curpasswordError: Boolean = false
)
