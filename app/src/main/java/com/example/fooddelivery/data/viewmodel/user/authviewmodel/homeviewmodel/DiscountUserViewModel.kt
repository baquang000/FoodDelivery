package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.GetDiscountItem
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class DiscountUserViewModel : ViewModel() {

    private val _discountUser = MutableStateFlow<List<GetDiscountItem>>(emptyList())
    val discountUser = _discountUser.asStateFlow()

    private val _isLoadDiscount = MutableStateFlow(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDiscountUserWithApi()
        }
    }

    private suspend fun getDiscountUserWithApi() {
        _isLoadDiscount.value = true
        try {
            _discountUser.value = RetrofitClient.discountAPIService.getByUser(id = ID)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _isLoadDiscount.value = false
        }
    }
}