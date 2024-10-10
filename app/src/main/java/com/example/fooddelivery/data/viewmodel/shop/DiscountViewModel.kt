package com.example.fooddelivery.data.viewmodel.shop

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.CreateDiscount
import com.example.fooddelivery.data.model.GetDiscountItem
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.untils.MoshiGlobal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiscountViewModel : ViewModel() {

    private val _discount = MutableStateFlow<List<GetDiscountItem>>(emptyList())
    val discount = _discount.asStateFlow()

    private val _isLoadDiscount = MutableStateFlow(false)
    val isLoadDiscount = _isLoadDiscount.asStateFlow()

    private val _discountDetails = MutableStateFlow<GetDiscountItem?>(null)
    val discountDetails = _discountDetails.asStateFlow()

    private val _isLoadDiscountDetails = MutableStateFlow(false)
    val isLoadDiscountDetails = _isLoadDiscountDetails.asStateFlow()

    private val _isLoadAdd = MutableStateFlow(false)
    val isLoadAdd = _isLoadAdd.asStateFlow()

    private val tag = ViewModel::class.java.simpleName
    var isFailer by mutableStateOf(false)
    var isSuccess by mutableStateOf(false)
    var errormessage by mutableStateOf<String?>(null)

    init {
        if (ID != 0) {
            viewModelScope.launch(Dispatchers.IO) {
                getDiscountWithApi(idShop = ID)
            }
        }
    }

    private suspend fun getDiscountWithApi(idShop: Int) {
        _isLoadDiscount.value = true
        try {
            _discount.value = RetrofitClient.discountAPIService.getByShop(idShop)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadDiscount.value = false
        }
    }

    suspend fun getDiscountDetails(id: Int) {
        _isLoadDiscountDetails.value = true
        try {
            _discountDetails.value = RetrofitClient.discountAPIService.getSingle(id)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadDiscountDetails.value = false
        }
    }

    suspend fun createDiscount(discount: CreateDiscount) {
        _isLoadAdd.value = true
        try {
            val response = RetrofitClient.discountAPIService.createDiscount(discount)
            if (response.isSuccessful) {
                Log.d("DiscountViewModel", "createDiscount: Success")
                isSuccess = true
            } else {
                val errorBody = response.errorBody()?.string()
                isFailer = true
                errormessage = errorBody?.let {
                    // Phân tích JSON để chỉ lấy thông điệp lỗi
                    MoshiGlobal.errorResponse(it)
                } ?: "Unknown error"
            }
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }finally {
            _isLoadAdd.value = false
        }
    }
}