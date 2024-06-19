package com.example.fooddelivery.data.viewmodel.profileviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.data.model.OrderFood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderFoodViewModel : ViewModel() {
    private val _orderConfirmStateFlow = MutableStateFlow<List<OrderFood>>(emptyList())
    val orderConfirmStateFlow = _orderConfirmStateFlow.asStateFlow()

    init {
        fetchOrderFood()
    }

    private fun fetchOrderFood() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("orderFood").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val orderList = mutableListOf<OrderFood>()
                        for (datasnap in snapshot.children) {
                            val foodlist = datasnap.getValue(OrderFood::class.java)
                            if (foodlist != null) {
                                orderList.add(foodlist)
                            }
                        }
                        viewModelScope.launch {
                            _orderConfirmStateFlow.value = orderList
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e(
                            "Firebase",
                            "Fetch order confirmation food failed",
                            error.toException()
                        )
                    }
                })
        }
    }
}