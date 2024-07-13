package com.example.fooddelivery.data.viewmodel.authviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.SignUpUIState
import com.example.fooddelivery.data.model.SignupUIEvent
import com.example.fooddelivery.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth

class SignupViewModel : ViewModel() {
    var signUpUIState = mutableStateOf(SignUpUIState())
    var allValicationPass = mutableStateOf(false)
    var signInProgress = mutableStateOf(false)

    var errormessage by mutableStateOf<String?>(null)
    var isError by mutableStateOf(false)
    var isSuccess by mutableStateOf(false) //3.7
    private val tag = SignupViewModel::class.simpleName

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

            is SignupUIEvent.CurPasswordChange -> {
                signUpUIState.value = signUpUIState.value.copy(curpassword = event.curpassword)
            }

            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
        validateDataWithRules()
    }

    private fun signUp() {
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
        val curpasswordResult = Validator.validateCurPassword(
            fCurPassWord = signUpUIState.value.curpassword,
            fPassWord = signUpUIState.value.password,
        )

        signUpUIState.value = signUpUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            curpasswordError = curpasswordResult.status
        )
        allValicationPass.value =
            emailResult.status && passwordResult.status && curpasswordResult.status

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
                    isSuccess = true // 3.7
                }
            }
            .addOnFailureListener { exception ->
                Log.d(tag, "Inside_addOnFailureListener")
                signInProgress.value = false
                isError = true
                errormessage = exception.localizedMessage ?: "An error occurred."
            }
    }
}