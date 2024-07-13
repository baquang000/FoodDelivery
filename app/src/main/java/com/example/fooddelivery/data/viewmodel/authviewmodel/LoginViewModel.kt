package com.example.fooddelivery.data.viewmodel.authviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.LoginUIEvent
import com.example.fooddelivery.data.model.LoginUIState
import com.example.fooddelivery.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    var loginUIState = mutableStateOf(LoginUIState())
    var allValicationPass = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)
    private val tag = LoginViewModel::class.simpleName
    private val _navigationHome = MutableStateFlow(false)
    val navigationHome = _navigationHome.asStateFlow()
    var isFailer by mutableStateOf(false)
    var errormessage by mutableStateOf<String?>(null)
    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChange -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
            }

            is LoginUIEvent.PasswordChange -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        validateLoginUIDataWithRules()
    }

    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(tag, "${it.isSuccessful}")
                    loginInProgress.value = false
                    _navigationHome.value = true

                }
            }
            .addOnFailureListener {
                Log.d(tag, "${it.message}")
                isFailer = true
                errormessage = it.localizedMessage ?: "An error occurred."
                loginInProgress.value = false
            }
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            fEmail = loginUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            fPassWord = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        allValicationPass.value = emailResult.status && passwordResult.status

    }
}