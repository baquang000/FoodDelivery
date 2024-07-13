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

    fun validateCurPassword(fCurPassWord: String, fPassWord: String): ValicationResult {
        return ValicationResult(fCurPassWord.isNotEmpty() && fCurPassWord.length >= 6 && fPassWord == fCurPassWord)
    }
}

data class ValicationResult(
    val status: Boolean = false
)