package com.example.fooddelivery.data.viewmodel.homeviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.newShop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {
    //shop information
    private val _shopProfileStateFlow = MutableStateFlow<List<newShop>>(emptyList())
    val shopProfileStateFlow: StateFlow<List<newShop>> = _shopProfileStateFlow.asStateFlow()
    private val _isLoadShop = MutableStateFlow(false)
    val isLoadShop = _isLoadShop.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    fun setIdShop(idShop: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getShopProfile(idShop = idShop)
        }
    }

    private suspend fun getShopProfile(idShop: String) {
        _isLoadShop.value = true
        try {
            _shopProfileStateFlow.value =
                RetrofitClient.shopAPIService.getInforShop(idShop = idShop)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadShop.value = false
        }
    }
}