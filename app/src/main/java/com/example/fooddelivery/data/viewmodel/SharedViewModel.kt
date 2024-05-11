package com.example.fooddelivery.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.FoodDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {
    private val _foodDetailStateFlow = MutableStateFlow<List<FoodDetails>>(emptyList())
    val foodDetailStateFlow: StateFlow<List<FoodDetails>> = _foodDetailStateFlow.asStateFlow()

    fun addFoodDetail(foodDetails: FoodDetails) {
        _foodDetailStateFlow.update { currentList ->
            val updatedList = currentList.toMutableList()
            val existingItem = updatedList.find { it.title == foodDetails.title }
            if (existingItem != null) {
                existingItem.quantity += foodDetails.quantity
            } else {
                updatedList.add(foodDetails.copy())
            }
            return@update updatedList.toList()
        }
    }

    fun updateFoodDetailQuantity(index: Int, newQuantity: Int) {
        _foodDetailStateFlow.update { list ->
            list.mapIndexed { curindex, fooddetail ->
                if (curindex == index) fooddetail.copy(quantity = newQuantity)
                else fooddetail
            }
        }
    }

    fun caculatorPrice(): Double {
        return _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }
    }


}

