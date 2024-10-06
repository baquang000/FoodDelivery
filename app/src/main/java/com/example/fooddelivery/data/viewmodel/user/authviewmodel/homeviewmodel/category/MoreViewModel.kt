package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoreViewModel : ViewModel() {
    //moreFood
    private val _moreFood = MutableStateFlow<List<Food>>(emptyList())
    val moreFood = _moreFood.asStateFlow()
    private val _isLoadMore = MutableStateFlow(false)
    val isLoadMore = _isLoadMore.asStateFlow()

    //tag viewmodel
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMoreWithApi()
        }
    }

    private suspend fun getMoreWithApi() {
        _isLoadMore.value = true
        try {
            _moreFood.value = RetrofitClient.foodAPIService.getCategory(8)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadMore.value = false
        }
    }

}