package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.GetComment
import com.example.fooddelivery.data.model.GetShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {
    //shop information
    private val _shopProfileStateFlow = MutableStateFlow<List<GetShopItem>>(emptyList())
    val shopProfileStateFlow: StateFlow<List<GetShopItem>> = _shopProfileStateFlow.asStateFlow()
    private val _isLoadShop = MutableStateFlow(false)
    val isLoadShop = _isLoadShop.asStateFlow()

    //save comment value
    private val _commentStateFlow = MutableStateFlow<List<GetComment>>(emptyList())
    val commentStateFlow = _commentStateFlow.asStateFlow()

    private val _isLoadComment = MutableStateFlow(false)
    val isLoadComment = _isLoadComment.asStateFlow()
    private val _countComent = MutableStateFlow(0)
    val countComent = _countComent.asStateFlow()


    private val _idShopStateFlow = MutableStateFlow(0)

    private val tag = ViewModel::class.java.simpleName


    fun setIdShop(idShop: Int) {
        _idShopStateFlow.value = idShop
        viewModelScope.launch(Dispatchers.IO) {
            getShopProfile(idShop = _idShopStateFlow.value)
            getComment(idShop = _idShopStateFlow.value)
            countCommentOfShop(idShop = _idShopStateFlow.value)
        }
    }

    private suspend fun getShopProfile(idShop: Int) {
        _isLoadShop.value = true
        try {
            _shopProfileStateFlow.value =
                RetrofitClient.shopAPIService.getShopAndFood(idShop = idShop)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadShop.value = false
        }
    }

    //getComment
    private suspend fun getComment(idShop: Int) {
        _isLoadComment.value = true
        try {
            _commentStateFlow.value =
                RetrofitClient.commentAPIService.getCommentByShop(idShop = idShop)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadComment.value = false
        }
    }

    private fun countCommentOfShop(idShop: Int) {
        _countComent.value = _commentStateFlow.value.count { it.idShop == idShop }
    }
}