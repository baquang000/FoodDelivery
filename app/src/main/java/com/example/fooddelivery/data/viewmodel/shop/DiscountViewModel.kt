package com.example.fooddelivery.data.viewmodel.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.GetDiscountItem
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiscountViewModel : ViewModel() {

    private val _discount = MutableStateFlow<List<GetDiscountItem>>(emptyList())
    val discount = _discount.asStateFlow()

    private val _isLoadDiscount = MutableStateFlow(false)
    val isLoadDiscount = _isLoadDiscount.asStateFlow()

    private val tag = ViewModel::class.java.simpleName

    init {
        if (ID != "") {
            viewModelScope.launch(Dispatchers.IO) {
                getDiscountWithApi(idShop = ID)
            }
        }
    }

    private suspend fun getDiscountWithApi(idShop: String) {
        _isLoadDiscount.value = true
        try {
            _discount.value = RetrofitClient.discountAPIService.getByShop(idShop)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadDiscount.value = false
        }
    }
}