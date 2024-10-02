package com.example.fooddelivery.data.viewmodel.user.authviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Auth
import com.example.fooddelivery.data.model.SignUpUIState
import com.example.fooddelivery.data.model.SignupUIEvent
import com.example.fooddelivery.data.model.UserInfor
import com.example.fooddelivery.data.rules.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    var signUpUIState = mutableStateOf(SignUpUIState())
    var allValicationPass = mutableStateOf(false)
    var signInProgress = mutableStateOf(false)

    var errormessage by mutableStateOf<String?>(null)
    var isError by mutableStateOf(false)
    var isSuccess by mutableStateOf(false) //3.7
    private val tag = SignupViewModel::class.simpleName


    suspend fun onEvent(event: SignupUIEvent) {
        validateDataWithRules()
        when (event) {
            is SignupUIEvent.emailChange -> {
                signUpUIState.value = signUpUIState.value.copy(email = event.email)
            }

            is SignupUIEvent.PasswordChange -> {
                signUpUIState.value =
                    signUpUIState.value.copy(password = event.password)
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

    private suspend fun signUp() {
        createUserWithApi(
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

    private suspend fun createUserWithApi(email: String, password: String) {
        signInProgress.value = true
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val respon = RetrofitClient.authAPIService.register(Auth(email, password))
                if (respon.isSuccessful) {
                    isSuccess = true // 3.7
                    val user = respon.body()!!.data
                    val newUser = UserInfor(
                        idUser = user.id,
                        name = null,
                        email = email,
                        numberPhone = null,
                        address = null,
                        dateOfBirth = null
                    )
                    RetrofitClient.userAPIService.createUser(newUser)
                } else {
                    errormessage = respon.body()?.message
                    isError = true
                }
            }
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            signInProgress.value = false
        }
    }
}