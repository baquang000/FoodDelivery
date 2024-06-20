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
    private val _orderFoodStateFlow = MutableStateFlow<List<OrderFood>>(emptyList())
    val orderFoodStateFlow = _orderFoodStateFlow.asStateFlow()

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
                            _orderFoodStateFlow.value = orderList
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

    fun canceledOrder(orderFood: OrderFood) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && orderFood.id != null) {
            val cancel = mapOf(
                "cancelled" to true
            )
            FirebaseDatabase.getInstance().getReference("orderFood").child(uid)
                .child(orderFood.id!!).updateChildren(cancel).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "cancel order success")
                        fetchOrderFood()
                    } else {
                        Log.e("Firebase", "cancel order failed", task.exception)
                    }
                }
        }
    }
}