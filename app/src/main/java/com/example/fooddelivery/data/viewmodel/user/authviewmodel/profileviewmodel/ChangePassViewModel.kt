package com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.changePassword
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.untils.MoshiGlobal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePassViewModel : ViewModel() {
    var currentPass by mutableStateOf("")
    var newPass by mutableStateOf("")
    var reNewPass by mutableStateOf("")
    var isCurPassNull by mutableStateOf(false)
    var isNewPassequalRePass by mutableStateOf(true)
    var isClickButton by mutableStateOf(false)
    var changePassSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    private val tag = ViewModel::class.java.simpleName
    suspend fun checkRulesPassWord() {
        isClickButton = true
        isCurPassNull = currentPass.isEmpty()
        isNewPassequalRePass = newPass == reNewPass
        if (!isCurPassNull && isNewPassequalRePass && isClickButton) {
            changePass(currentPass, newPass)
        }
    }

    private suspend fun changePass(currentPass: String, newPass: String) {
        try {
            val changePass =
                changePassword(id = ID, currentPassword = currentPass, newPassword = newPass)
            viewModelScope.launch(Dispatchers.IO) {
                val response = RetrofitClient.authAPIService.changePassword(changePass)
                if (response.isSuccessful) {
                    changePassSuccess = true
                } else {
                    changePassSuccess = false
                    val errorBody = response.errorBody()?.string()
                    errorMessage = errorBody?.let {
                        // Phân tích JSON để chỉ lấy thông điệp lỗi
                        MoshiGlobal.errorResponse(it)
                    } ?: "Unknown error"
                }
            }
        } catch (e: Exception) {
            Log.d(tag, "changePass: ${e.message}")
            changePassSuccess = false
            errorMessage = e.message.toString()
        }
    }
}