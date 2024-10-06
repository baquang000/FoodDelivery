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

class PizzaViewModel : ViewModel() {
    //pizzaFood
    private val _pizzaFood = MutableStateFlow<List<Food>>(emptyList())
    val pizzaFood = _pizzaFood.asStateFlow()
    private val _isLoadPizza = MutableStateFlow(false)
    val isLoadPizza = _isLoadPizza.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getPizzaWithApi()
        }
    }

    private suspend fun getPizzaWithApi() {
        _isLoadPizza.value = true
        try {
            _pizzaFood.value = RetrofitClient.foodAPIService.getCategory(1)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadPizza.value = false
        }
    }
}