package com.example.fooddelivery.rules

object Validator {
    fun validateSdt(fSDT: String): ValicationResult {
        return ValicationResult(
            (fSDT.isNotEmpty() && fSDT.length >= 9 && fSDT.length <= 11)
        )
    }

    fun validatePassword(fPassWord: String): ValicationResult {
        return ValicationResult(fPassWord.isNotEmpty())
    }
}

data class ValicationResult(
    val status: Boolean = false
)