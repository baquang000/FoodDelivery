package com.example.fooddelivery.data.viewmodel.authviewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Auth
import com.example.fooddelivery.data.model.LoginUIEvent
import com.example.fooddelivery.data.model.LoginUIState
import com.example.fooddelivery.data.rules.Validator
import com.example.fooddelivery.untils.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class LoginViewModel : ViewModel() {

    var loginUIState = mutableStateOf(LoginUIState())
    var allValicationPass = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)
    private val tag = LoginViewModel::class.simpleName
    private val _navigationHome = MutableStateFlow(false)
    val navigationHome = _navigationHome.asStateFlow()
    var isFailer by mutableStateOf(false)
    var errormessage by mutableStateOf<String?>(null)
    suspend fun onEvent(event: LoginUIEvent, context: Context) {
        when (event) {
            is LoginUIEvent.EmailChange -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
            }

            is LoginUIEvent.PasswordChange -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login(context = context)
            }
        }
        validateLoginUIDataWithRules()
    }

    private suspend fun login(context: Context) {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val respon = RetrofitClient.authAPIService.login(Auth(email, password))
                if (respon.isSuccessful) {
                    val token = respon.body()?.data?.accessToken
                        ?: return@launch // Handle missing token gracefully (e.g., show error)
                    val cryptoManager = TokenManager()
                    val bytes = token.encodeToByteArray()
                    val file = File(context.filesDir, "token.txt")
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    val fos = FileOutputStream(file)
                    cryptoManager.encrypt(bytes = bytes, outputStream = fos).decodeToString()

                    loginInProgress.value = false
                    _navigationHome.value = true
                } else {
                    // Handle unsuccessful login (e.g., invalid credentials)
                    loginInProgress.value = false
                    val errorBody = respon.errorBody()?.string() ?: "Login failed"
                    isFailer = true
                    errormessage = errorBody
                    // Optionally: Show a toast or update UI with the error message
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "${e.message}")
            isFailer = true
            errormessage = e.localizedMessage ?: "An error occurred."
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

    fun googleLoginSuccess() {
        _navigationHome.value = true
    }
}