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

class CategoryViewModel : ViewModel() {
    //Chicken
    private val _categoryFood = MutableStateFlow<List<Food>>(emptyList())
    val categoryFood = _categoryFood.asStateFlow()
    private val _isLoadCategory = MutableStateFlow(false)
    val isLoadCategory = _isLoadCategory.asStateFlow()
    private val tag = ViewModel::class.java.simpleName


     suspend fun initCategory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoryWithApi(id)
        }
    }

    private suspend fun getCategoryWithApi(id: Int) {
        _isLoadCategory.value = true
        try {
            _categoryFood.value = RetrofitClient.foodAPIService.getCategory(id)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadCategory.value = false
        }
    }
}