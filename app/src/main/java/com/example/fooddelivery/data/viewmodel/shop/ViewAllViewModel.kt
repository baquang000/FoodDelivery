package com.example.fooddelivery.data.viewmodel.shop

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.GetComment
import com.example.fooddelivery.data.model.UpdateBestFood
import com.example.fooddelivery.data.model.UpdateDescriptionFood
import com.example.fooddelivery.data.model.UpdateImageFood
import com.example.fooddelivery.data.model.UpdatePriceFood
import com.example.fooddelivery.data.model.UpdateShowFood
import com.example.fooddelivery.data.model.UpdateTitleFood
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ViewAllViewModel : ViewModel() {
    var isTitleEmpty by mutableStateOf(false)
    var isDescriptionEmpty by mutableStateOf(false)
    var isPriceInvalid by mutableStateOf(false)

    //save comment value
    private val _commentStateFlow = MutableStateFlow<List<GetComment>>(emptyList())
    val commentStateFlow = _commentStateFlow.asStateFlow()

    private val _isLoadComment = MutableStateFlow(false)
    val isLoadComment = _isLoadComment.asStateFlow()

    /// all food
    private val _allFood = MutableStateFlow<List<Food>>(emptyList())
    val allFood = _allFood.asStateFlow()
    private val tag = ViewModel::class.java.simpleName
    private val _isLoadAllFood = MutableStateFlow(false)
    val isLoadAllFood = _isLoadAllFood.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFoodWithApi()
            getCommentWithApi()
        }
    }

    suspend fun getAllFoodWithApi() {
        if (ID != "") {
            _isLoadAllFood.value = true
            try {
                _allFood.value = RetrofitClient.foodAPIService.getAllFood(ID)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadAllFood.value = false
            }
        }
    }

    suspend fun updateIsBestFood(idFood: Int, isBestFood: Boolean) {
        if (ID != "") {
            val update = UpdateBestFood(idFood = idFood, bestFood = isBestFood)
            try {
                RetrofitClient.foodAPIService.updateBestFood(ID, update)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    suspend fun updateIsShowFood(showFood: Boolean, id: Int) {
        if (ID != "") {
            val update = UpdateShowFood(idFood = id, showFood = showFood)
            try {
                RetrofitClient.foodAPIService.updateShowFood(ID, update)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    suspend fun updateTitle(title: String, id: Int) {
        if (title.isEmpty()) {
            isTitleEmpty = true
            return
        }
        if (ID != "") {
            val update = UpdateTitleFood(
                title = title,
                idFood = id
            )
            try {
                RetrofitClient.foodAPIService.updateTitleFood(ID, update)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    suspend fun updatePrice(price: Double, id: Int) {
        if (price <= 0 || price.isNaN()) {
            isPriceInvalid = true
            return
        }
        if (ID != "") {
            val update = UpdatePriceFood(
                price = price,
                idFood = id
            )
            try {
                RetrofitClient.foodAPIService.updatePriceFood(ID, update)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    suspend fun updateImagpath(imagePath: String, id: Int) {
        if (ID != "") {
            val update = UpdateImageFood(
                imagePath = imagePath,
                idFood = id
            )
            try {
                RetrofitClient.foodAPIService.updateImageFood(ID, update)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    suspend fun updateDescription(description: String, id: Int) {
        if (description.isEmpty()) {
            isDescriptionEmpty = true

            return
        }
        if (ID != "") {
            val update = UpdateDescriptionFood(
                description = description,
                idFood = id
            )
            try {
                RetrofitClient.foodAPIService.updateDescriptionFood(ID, update)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    //getComment
    private suspend fun getCommentWithApi() {
        if (ID != "") {
            _isLoadComment.value = true
            try {
                _commentStateFlow.value = RetrofitClient.commentAPIService.getCommentByShop(ID)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadComment.value = false
            }
        }
    }
}