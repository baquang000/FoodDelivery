package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewAllViewModel : ViewModel() {
    private val _allFood = MutableStateFlow<List<Food>>(emptyList())
    val allFood = _allFood.asStateFlow()
    private val tag = ViewModel::class.java.simpleName
    private val _isLoadAllFood = MutableStateFlow(false)
    val isLoadAllFood = _isLoadAllFood.asStateFlow()

    private suspend fun getAllFoodWithApi(token: String) {
        try {
            val tokenBearer = "Bearer $token"
            _allFood.value = RetrofitClient.foodAPIService.getAllFood(tokenBearer)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadAllFood.value = false
        }
    }

    fun getToken(token: String) {
        Log.d("Token", token)
        viewModelScope.launch(Dispatchers.IO) {
            getAllFoodWithApi(token)
        }
    }
}