package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DrinkViewModel : ViewModel() {
    //drinkFood
    private val _drinkFood = MutableStateFlow<List<Food>>(emptyList())
    val drinkFood = _drinkFood.asStateFlow()
    private val _isLoadDrink = MutableStateFlow(false)
    val isLoadDrink = _isLoadDrink.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDrinkWithApi()
        }
    }

    private suspend fun getDrinkWithApi() {
        _isLoadDrink.value = true
        try {
            _drinkFood.value = RetrofitClient.foodAPIService.getCategory(7)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadDrink.value = false
        }
    }
}