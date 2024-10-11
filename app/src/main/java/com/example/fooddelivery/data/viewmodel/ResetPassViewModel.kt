package com.example.fooddelivery.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.ForgetPassDto
import com.example.fooddelivery.untils.MoshiGlobal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResetPassViewModel : ViewModel() {
    private val tag = ResetPassViewModel::class.simpleName
    private val _isSuccess = MutableStateFlow<Boolean?>(null)
    val isSuccess = _isSuccess.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()
    private val _isLoad = MutableStateFlow(false)
    val isLoad = _isLoad.asStateFlow()
    suspend fun sendLinkResetPassword(email: String) {
        val payload = ForgetPassDto(
            email = email
        )
        viewModelScope.launch {
            try {
                _isLoad.value = true
                Log.e("FORGET", _isLoad.value.toString() + "load")
                val response = RetrofitClient.authAPIService.forgetPassword(payload)
                if (response.isSuccessful) {
                    _isSuccess.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = errorBody?.let {
                        // Phân tích JSON để chỉ lấy thông điệp lỗi
                        MoshiGlobal.errorResponse(it)
                    } ?: "Unknown error"
                    _isSuccess.value = false
                    Log.e("FORGET", _errorMessage.value.toString())
                    Log.e("FORGET", _isSuccess.value.toString())
                }
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoad.value = false
            }
        }
    }
}