package com.example.fooddelivery.data.viewmodel.authviewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.SignUpUIState
import com.example.fooddelivery.data.model.SignupUIEvent
import com.example.fooddelivery.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignupViewModel : ViewModel() {
    var signUpUIState = mutableStateOf(SignUpUIState())
    var allValicationPass = mutableStateOf(false)
    var signInProgress = mutableStateOf(false)
    private val tag = SignupViewModel::class.simpleName
    private val _navigationHome = MutableStateFlow(false)
    val navigationHome = _navigationHome.asStateFlow()
    fun onEvent(event: SignupUIEvent) {
        validateDataWithRules()
        when (event) {
            is SignupUIEvent.emailChange -> {
                signUpUIState.value = signUpUIState.value.copy(email = event.email)
                printState()
            }

            is SignupUIEvent.PasswordChange -> {
                signUpUIState.value =
                    signUpUIState.value.copy(password = event.password)
                printState()
            }

            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        Log.d(tag, "Inside_signUp")
        printState()

        createUserInFirebase(
            email = signUpUIState.value.email,
            password = signUpUIState.value.password
        )
    }

    private fun validateDataWithRules() {
        val emailResult = Validator.validateEmail(
            fEmail = signUpUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            fPassWord = signUpUIState.value.password
        )
        Log.d(tag, "Inside_validateDataWithRules")
        Log.d(tag, "sdtResult = $emailResult")
        Log.d(tag, "passwordResult = $passwordResult")

        signUpUIState.value = signUpUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        allValicationPass.value = emailResult.status && passwordResult.status

    }

    private fun printState() {
        Log.d(tag, "Inside_printState")
        Log.d(tag, signUpUIState.value.toString())
    }

    private fun createUserInFirebase(email: String, password: String) {
        signInProgress.value = true
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(tag, "Inside_addOnCompleteListener")
                if (it.isSuccessful) {
                    Log.d(
                        tag, "isSuccessful = ${it.isSuccessful}"
                    )
                    signInProgress.value = false
                    _navigationHome.value = true
                }
            }
            .addOnFailureListener {
                Log.d(tag, "Inside_addOnFailureListener")
                signInProgress.value = false
            }
    }

    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }

}