package com.example.fooddelivery.data.viewmodel.homeviewmodel.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.newFood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BurgerViewModel : ViewModel() {

    //burger
    private val _burgerFood = MutableStateFlow<List<newFood>>(emptyList())
    val burgerFood = _burgerFood.asStateFlow()
    private val _isLoadBurger = MutableStateFlow(false)
    val isLoadBurger = _isLoadBurger.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBurgerWithApi()
        }
    }

    private suspend fun getBurgerWithApi() {
        _isLoadBurger.value = true
        try {
            _burgerFood.value = RetrofitClient.foodAPIService.getCategory(1)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadBurger.value = false
        }
    }
}