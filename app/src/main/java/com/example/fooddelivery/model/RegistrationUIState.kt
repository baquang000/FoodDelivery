package com.example.fooddelivery.model

data class RegistrationUIState(
    var sdt: String = "",
    var password: String = "",

    var sdtError: Boolean = false,
    var passwordError: Boolean = false
)
