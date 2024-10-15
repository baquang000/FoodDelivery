package com.example.fooddelivery.data.viewmodel.shop.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class ChartsFoodViewModel : ViewModel() {
    /// all food
    private val _allFood = MutableStateFlow<List<Food>>(emptyList())
    val allFood = _allFood.asStateFlow()
    private val _isLoadAllFood = MutableStateFlow(false)
    val isLoadAllFood = _isLoadAllFood.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFoodWithApi()
        }
    }

    private suspend fun getAllFoodWithApi() {
        if (ID != 0) {
            _isLoadAllFood.value = true
            try {
                _allFood.value = RetrofitClient.foodAPIService.getFoodByShop(ID)
            } catch (e: Exception) {
                throw e
                e.printStackTrace()
            } finally {
                _isLoadAllFood.value = false
            }
        }
    }
}