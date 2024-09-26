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

class ChickenViewModel : ViewModel() {
    //Chicken
    private val _chickenFood = MutableStateFlow<List<newFood>>(emptyList())
    val chickenFood = _chickenFood.asStateFlow()
    private val _isLoadChicken = MutableStateFlow(false)
    val isLoadChicken = _isLoadChicken.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getChickenWithApi()
        }
    }


    private suspend fun getChickenWithApi() {
        _isLoadChicken.value = true
        try {
            _chickenFood.value = RetrofitClient.foodAPIService.getCategory(2)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadChicken.value = false
        }
    }
}