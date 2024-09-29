package com.example.fooddelivery.data.viewmodel.homeviewmodel.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HotDogViewModel : ViewModel() {

    //hotdogFood
    private val _hotdogFood = MutableStateFlow<List<Food>>(emptyList())
    val hotdogFood = _hotdogFood.asStateFlow()
    private val _isLoadHotdog = MutableStateFlow(false)
    val isLoadHoDog = _isLoadHotdog.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHotdogWithApi()
        }
    }

    private suspend fun getHotdogWithApi() {
        _isLoadHotdog.value = true
        try {
            _hotdogFood.value = RetrofitClient.foodAPIService.getCategory(5)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadHotdog.value = false
        }
    }
}