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

class SushiViewModel : ViewModel() {
    //sushiFood
    private val _sushiFood = MutableStateFlow<List<newFood>>(emptyList())
    val sushiFood = _sushiFood.asStateFlow()
    private val _isLoadSushi = MutableStateFlow(false)
    val isLoadSushi = _isLoadSushi.asStateFlow()

    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSushiWithApi()
        }
    }

    private suspend fun getSushiWithApi() {
        _isLoadSushi.value = true
        try {
            _sushiFood.value = RetrofitClient.foodAPIService.getCategory(3)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadSushi.value = false
        }
    }
}