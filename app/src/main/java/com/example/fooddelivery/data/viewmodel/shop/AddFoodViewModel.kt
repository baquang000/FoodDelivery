package com.example.fooddelivery.data.viewmodel.shop

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.CreateFood
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddFoodViewModel : ViewModel() {
    var isTitleEmpty by mutableStateOf(false)
    var isDescriptionEmpty by mutableStateOf(false)
    var isPriceInvalid by mutableStateOf(false)
    var isImageUrlEmpty by mutableStateOf(false)
    var isTimeInvalid by mutableStateOf(false)
    private val _idShop = FirebaseAuth.getInstance().currentUser?.uid
    private val tag = ViewModel::class.java.simpleName
    private val _isLoadingAddFood = MutableStateFlow(false)
    val isLoadingAddFood = _isLoadingAddFood.asStateFlow()
    suspend fun addNewFood(
        title: String,
        description: String,
        price: Double,
        imageUrl: String,
        bestFood: Boolean,
        category: Int,
        loc: Int,
        time: Int
    ) {
        _isLoadingAddFood.value = true
        if (_idShop != null) {
            var priceId by mutableIntStateOf(0)
            var timeid by mutableIntStateOf(0)
            if (title.isEmpty()) {
                isTitleEmpty = true
                _isLoadingAddFood.value = false
                return
            }
            if (description.isEmpty()) {
                isDescriptionEmpty = true
                _isLoadingAddFood.value = false
                return
            }
            if (price <= 0 || price.isNaN()) {
                isPriceInvalid = true
                _isLoadingAddFood.value = false
                return
            } else {
                priceId = when (price) {
                    in 0.0..150000.0 -> {
                        0
                    }

                    in 150001.0..300000.0 -> {
                        1
                    }

                    else -> {
                        2
                    }
                }
            }
            if (imageUrl.isBlank()) {
                isImageUrlEmpty = true
                _isLoadingAddFood.value = false
                return
            }
            if (time <= 0) {
                isTimeInvalid = true
                _isLoadingAddFood.value = false
                return
            } else {
                timeid = when (time) {
                    in 0..10 -> {
                        0
                    }

                    in 11..30 -> {
                        1
                    }

                    else -> {
                        2
                    }
                }
            }
            val newFood = CreateFood(
                bestFood = bestFood,
                categoryId = category,
                description = description,
                imagePath = imageUrl,
                locationId = loc,
                price = price,
                priceId = priceId,
                star = 5.0,
                timeId = timeid,
                timeValue = time,
                title = title,
                idShop = _idShop,
                showFood = false,
            )
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    RetrofitClient.foodAPIService.createFood(newFood)
                } catch (e: Exception) {
                    Log.d(tag, "error addNewFood: ${e.message}")
                } finally {
                    _isLoadingAddFood.value = false
                }
            }
        }
    }
}