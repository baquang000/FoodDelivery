package com.example.fooddelivery.data.rules

object Validator {
    fun validateEmail(fEmail: String): ValicationResult {
        return ValicationResult(
            (fEmail.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(fEmail).matches())
        )
    }

    fun validatePassword(fPassWord: String): ValicationResult {
        return ValicationResult(fPassWord.isNotEmpty() && fPassWord.length >= 6)
    }
}

data class ValicationResult(
    val status: Boolean = false
)