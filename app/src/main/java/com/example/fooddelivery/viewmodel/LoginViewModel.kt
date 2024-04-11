package com.example.fooddelivery.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.model.RegistrationUIState
import com.example.fooddelivery.model.UIEvent
import com.example.fooddelivery.rules.Validator

class LoginViewModel : ViewModel() {
    var registrationUIState = mutableStateOf(RegistrationUIState())
    private val tag = ViewModel::class.simpleName
    fun onEvent(event: UIEvent) {
        validateDataWithRules()
        when (event) {
            is UIEvent.SdtChange -> {
                registrationUIState.value = registrationUIState.value.copy(sdt = event.sdt)
                printState()
            }

            is UIEvent.PasswordChange -> {
                registrationUIState.value =
                    registrationUIState.value.copy(password = event.password)
                printState()
            }

            is UIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        Log.d(tag, "Inside_signUp")
        printState()

        validateDataWithRules()
    }

    private fun validateDataWithRules() {
        val sdtResult = Validator.validateSdt(
            fSDT = registrationUIState.value.sdt
        )
        val passwordResult = Validator.validatePassword(
            fPassWord = registrationUIState.value.password
        )
        Log.d(tag, "Inside_validateDataWithRules")
        Log.d(tag, "sdtResult = $sdtResult")
        Log.d(tag, "passwordResult = $passwordResult")

        registrationUIState.value = registrationUIState.value.copy(
            sdtError = sdtResult.status,
            passwordError = passwordResult.status
        )
    }

    private fun printState() {
        Log.d(tag, "Inside_printState")
        Log.d(tag, registrationUIState.value.toString())
    }
}