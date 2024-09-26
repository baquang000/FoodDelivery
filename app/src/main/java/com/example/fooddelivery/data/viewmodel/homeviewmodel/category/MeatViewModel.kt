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

class MeatViewModel : ViewModel() {
    //meatFood
    private val _meatFood = MutableStateFlow<List<newFood>>(emptyList())
    val meatFood = _meatFood.asStateFlow()
    private val _isLoadMeat = MutableStateFlow(false)
    val isLoadMeat = _isLoadMeat.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMeatWithApi()
        }
    }

    private suspend fun getMeatWithApi() {
        _isLoadMeat.value = true
        try {
            _meatFood.value = RetrofitClient.foodAPIService.getCategory(4)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadMeat.value = false
        }
    }
}