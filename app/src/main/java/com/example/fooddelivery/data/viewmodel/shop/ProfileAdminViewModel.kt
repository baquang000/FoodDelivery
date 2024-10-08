package com.example.fooddelivery.data.viewmodel.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Shop
import com.example.fooddelivery.data.model.UpdateShopInfor
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileAdminViewModel : ViewModel() {
    ///shop
    private val _shopInfor = MutableStateFlow<Shop?>(null)
    val shopInfor = _shopInfor.asStateFlow()
    private val _isLoadShopInfor = MutableStateFlow(false)
    val isLoadShopInfor = _isLoadShopInfor.asStateFlow()

    ///
    private val tag = ViewModel::class.java.simpleName

    init {
        if (ID != 0) {
            viewModelScope.launch(Dispatchers.IO) {
                getUserData(ID)
            }
        }
    }

    suspend fun saveUserData(
        titleShop: String,
        nameShop: String,
        address: String,
        email: String,
        phoneNumber: String,
        imageUrl: String
    ) {
        if (ID != 0) {
            val update = UpdateShopInfor(
                titleShop = titleShop,
                name = nameShop,
                address = address,
                email = email,
                phoneNumber = phoneNumber,
                imageUrl = imageUrl
            )
            try {
                RetrofitClient.shopAPIService.updateInforShop(ID, update)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    private suspend fun getUserData(id: Int) {
        _isLoadShopInfor.value = true
        try {
            _shopInfor.value = RetrofitClient.shopAPIService.getInforOfShop(id)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadShopInfor.value = false
        }
    }


}