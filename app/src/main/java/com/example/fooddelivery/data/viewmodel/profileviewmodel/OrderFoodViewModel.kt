package com.example.fooddelivery.data.viewmodel.profileviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.data.model.Calender
import com.example.fooddelivery.data.model.Comment
import com.example.fooddelivery.data.model.Food
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

    // star
    private val _allFoodStateFlow = MutableStateFlow<List<Food>>(emptyList())

    var orderIdComment by mutableStateOf("")
    var commentSuccess by mutableStateOf<Boolean?>(null)

    init {
        fetchOrderFood()
    }

    private fun fetchOrderFood() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("orderFood")
                .addValueEventListener(object : ValueEventListener {
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
                        Log.d("Firebase","Get orderfood all success full")
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
                    } else {
                        Log.e("Firebase", "cancel order failed", task.exception)
                    }
                }
        }
    }

    // đặt lại
    fun notCancelOrder(orderFood: OrderFood) {
        val time = Calender().getCalender()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && orderFood.idUser == uid) {
            val cancel = mapOf(
                "foodback" to true,
                "time" to time
            )
            FirebaseDatabase.getInstance().getReference("orderFood").child(orderFood.idOrder)
                .updateChildren(cancel).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "foodback order success")
                    } else {
                        Log.e("Firebase", "foodback order failed", task.exception)
                    }
                }
        }
    }

    //Hoàn thành
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
                    } else {
                        Log.e("Firebase", "delivered order failed", task.exception)
                    }
                }
        }
    }

    fun isCommented(orderFood: OrderFood) {
        val time = Calender().getCalender()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null && orderFood.idUser == uid) {
            val delivered = mapOf(
                "comment" to true,
                "time" to time
            )
            FirebaseDatabase.getInstance().getReference("orderFood").child(orderFood.idOrder)
                .updateChildren(delivered).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "comment order success")
                    } else {
                        Log.e("Firebase", "comment order failed", task.exception)
                    }
                }
        }
    }

    fun commentFood(comment: Comment) {
        getAllFood()
        val newStar = getNewStar(comment = comment)
        val time = Calender().getCalender()
        val commentNew = comment.copy(
            time = time
        )
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val commentRef = FirebaseDatabase.getInstance().getReference("comment")
            commentRef.get().addOnSuccessListener { snapshot ->
                val maxId = snapshot.children.mapNotNull { it.key?.toIntOrNull() }.maxOrNull() ?: 0
                val newId = if (maxId == 0) 0 else maxId + 1
                commentRef.child(newId.toString()).setValue(commentNew)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Firebase", "Comment successfully")
                            commentSuccess = true
                            if (newStar != 0.0) {
                                changeStar(star = newStar, idFood = comment.idFood.toString())
                            }
                        } else {
                            commentSuccess = false
                            Log.e("Firebase", "Comment failed", task.exception)
                        }
                    }
            }.addOnFailureListener { exception ->
                commentSuccess = false
                Log.e("Firebase", "Failed to comment", exception)
            }
        }
    }

    private fun getAllFood() {
        val templist = mutableListOf<Food>()
        FirebaseDatabase.getInstance().getReference("Foods")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnap in snapshot.children) {
                        val food = datasnap.getValue(Food::class.java)
                        if (food != null) {
                            templist.add(food)
                        }
                    }
                    _allFoodStateFlow.value = templist
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "OrderFoodViewModel",
                        "Failed to fetch all food in fun getAllFood"
                    )
                }

            })
    }

    private fun getNewStar(comment: Comment): Double {
        var newStar = 0.0
        _allFoodStateFlow.value.forEach { food ->
            if (comment.idFood == food.Id) {
                newStar = (comment.rating.toDouble() + food.Star) / 2.0
                Log.d("OrderFoodViewModel", "set new star: $newStar")
            } else {
                Log.e("OrderFoodViewModel", "get new star failed")
            }
        }
        return newStar
    }

    private fun changeStar(star: Double, idFood: String) {
        val newStar = mapOf(
            "Star" to star
        )
        FirebaseDatabase.getInstance().getReference("Foods").child(idFood).updateChildren(newStar)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("OrderFoodViewModel", "update new star success")
                } else {
                    Log.e("OrderFoodViewModel", "Failed to comment", task.exception)
                }
            }
    }
}
