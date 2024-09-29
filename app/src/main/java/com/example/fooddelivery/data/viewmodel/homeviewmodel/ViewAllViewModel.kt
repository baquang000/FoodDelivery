package com.example.fooddelivery.data.viewmodel.homeviewmodel

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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFoodWithApi()
        }
    }

    private suspend fun getAllFoodWithApi() {
        try {
            _allFood.value = RetrofitClient.foodAPIService.getAllFood()
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadAllFood.value = false
        }
    }
}