package com.example.fooddelivery.data.viewmodel.profileviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.data.model.Calender
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
            FirebaseDatabase.getInstance().getReference("orderFood")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val orderList = mutableListOf<OrderFood>()
                        for (datasnap in snapshot.children) {
                            val foodlist = datasnap.getValue(OrderFood::class.java)
                            if (foodlist != null && foodlist.idUser == userId) {
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
        val time = Calender().getCalender()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val cancel = mapOf(
                "cancelled" to true,
                "time" to time
            )
            FirebaseDatabase.getInstance().getReference("orderFood").child(orderFood.idOrder)
                .updateChildren(cancel).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "cancel order success")
                        fetchOrderFood()
                    } else {
                        Log.e("Firebase", "cancel order failed", task.exception)
                    }
                }
        }
    }

    fun notCancelOrder(orderFood: OrderFood) {
        val time = Calender().getCalender()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && orderFood.idUser == uid) {
            val cancel = mapOf(
                "cancelled" to false,
                "time" to time
            )
            FirebaseDatabase.getInstance().getReference("orderFood").child(orderFood.idOrder)
                .updateChildren(cancel).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "cancel order success")
                        fetchOrderFood()
                    } else {
                        Log.e("Firebase", "cancel order failed", task.exception)
                    }
                }
        }
    }

    fun deliveredOrder(orderFood: OrderFood) {
        val time = Calender().getCalender()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && orderFood.idUser == uid) {
            val delivered = mapOf(
                "delivered" to true,
                "time" to time
            )
            FirebaseDatabase.getInstance().getReference("orderFood").child(orderFood.idOrder)
                .updateChildren(delivered).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "delivered order success")
                        fetchOrderFood()
                    } else {
                        Log.e("Firebase", "delivered order failed", task.exception)
                    }
                }
        }
    }

    fun notDeliveredOrder(orderFood: OrderFood) {
        val time = Calender().getCalender()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && orderFood.idUser == uid) {
            val delivered = mapOf(
                "delivered" to false,
                "comfirm" to false,
                "time" to time
            )
            FirebaseDatabase.getInstance().getReference("orderFood").child(orderFood.idOrder)
                .updateChildren(delivered).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "delivered order success")
                        fetchOrderFood()
                    } else {
                        Log.e("Firebase", "delivered order failed", task.exception)
                    }
                }
        }
    }
}