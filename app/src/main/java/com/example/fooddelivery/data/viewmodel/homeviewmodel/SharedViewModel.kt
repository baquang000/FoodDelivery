package com.example.fooddelivery.data.viewmodel.homeviewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.DiscountCode
import com.example.fooddelivery.data.model.DiscountCodeState
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.model.OrderFood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {
    private val _foodDetailStateFlow = MutableStateFlow<List<FoodDetails>>(emptyList())
    val foodDetailStateFlow: StateFlow<List<FoodDetails>> = _foodDetailStateFlow.asStateFlow()

    val discountCode: MutableState<DiscountCodeState> = mutableStateOf(DiscountCodeState.Empty)

    private val _discountCodeValue = MutableStateFlow(0f)
    val discountCodeValue: StateFlow<Float> = _discountCodeValue.asStateFlow()

    private val _sumPrice = MutableStateFlow(0f)
    val sumPrice: StateFlow<Float> = _sumPrice.asStateFlow()

    init {
        fetchDiscountCodeFromFirebase()
    }

    private fun sumPrice() {
        val totalPrice =
            _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }.toFloat()
        val discountvalue = _discountCodeValue.value
        _sumPrice.value = if (discountvalue == 15000f) {
            totalPrice + 15000 - discountvalue
        } else {
            totalPrice - totalPrice * discountvalue + 15000
        }
    }

    fun getDiscountCodeValue(value: Float) {
        _discountCodeValue.value = value
        sumPrice()
    }

    private fun fetchDiscountCodeFromFirebase() {
        val emptyList = mutableListOf<DiscountCode>()
        discountCode.value = DiscountCodeState.Loading
        FirebaseDatabase.getInstance().getReference("DiscountCode")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val discountCodeList = dataSnap.getValue(DiscountCode::class.java)
                        if (discountCodeList != null) {
                            emptyList.add(discountCodeList)
                        }
                    }
                    discountCode.value = DiscountCodeState.Success(emptyList)
                }

                override fun onCancelled(error: DatabaseError) {
                    discountCode.value = DiscountCodeState.Failure(error.message)
                }
            })
    }

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
        sumPrice()
    }

    fun updateFoodDetailQuantity(index: Int, newQuantity: Int) {
        _foodDetailStateFlow.update { list ->
            list.mapIndexed { curindex, fooddetail ->
                if (curindex == index) fooddetail.copy(quantity = newQuantity)
                else fooddetail
            }
        }
        sumPrice()
    }

    fun caculatorPrice(): Double {
        return _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }
    }

    fun addFoodHistoryAndDelete() {
        val foodList = _foodDetailStateFlow.value
        val orderlist = OrderFood(listFood = foodList, sumPrice = _sumPrice.value)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val orderRef =
                FirebaseDatabase.getInstance().getReference("orderFood").child(userId).push()
            orderlist.id = orderRef.key
            orderRef.setValue(orderlist).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Save success")
                    _foodDetailStateFlow.value = emptyList()
                } else {
                    Log.e("Firebase", "Save failed", task.exception)
                }
            }
        }
    }
}

