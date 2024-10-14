package com.example.fooddelivery.data.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Auth
import com.example.fooddelivery.data.model.LoginUIEvent
import com.example.fooddelivery.data.model.LoginUIState
import com.example.fooddelivery.data.rules.Validator
import com.example.fooddelivery.untils.MoshiGlobal
import com.example.fooddelivery.untils.TokenManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
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
        val timeoutMillis = 8000L // 8 seconds in milliseconds
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        try {
            withTimeout(timeoutMillis) {
                val respon = withContext(Dispatchers.IO) {
                    RetrofitClient.authAPIService.login(Auth(email, password))
                }
                if (respon.isSuccessful) {
                    val token = respon.body()?.data?.accessToken ?: return@withTimeout
                    val id = respon.body()?.data?.rest?.id
                        ?: return@withTimeout // Handle missing token gracefully (e.g., show error)
                    ID = id.toInt()
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
                    val errorBody = respon.errorBody()?.string()
                    isFailer = true
                    errormessage = errorBody?.let {
                        // Phân tích JSON để chỉ lấy thông điệp lỗi
                        MoshiGlobal.errorResponse(it)
                    } ?: "Unknown error"
                    // Optionally: Show a toast or update UI with the error message
                }
            }
        } catch (e: TimeoutCancellationException) {
            // Handle timeout specifically
            loginInProgress.value = false
            isFailer = true
            errormessage = "Login timed out. Please try again."
        } catch (e: Exception) {
            if (e is CancellationException) throw e
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
            emailError = emailResult.status, passwordError = passwordResult.status
        )
        allValicationPass.value = emailResult.status && passwordResult.status

    }

    fun googleLoginSuccess() {
        _navigationHome.value = true
    }

}

var ID = 0