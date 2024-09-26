package com.example.fooddelivery.data.viewmodel.profileviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Calender
import com.example.fooddelivery.data.model.Comment
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.GetFoodDetail
import com.example.fooddelivery.data.model.GetOrder
import com.example.fooddelivery.data.model.OrderFood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderFoodViewModel : ViewModel() {
    //// order
    private val _orderFoodStateFlow = MutableStateFlow<List<GetOrder>>(emptyList())
    val orderFoodStateFlow = _orderFoodStateFlow.asStateFlow()

    //is loading
    private val _isLoadingOrder = MutableStateFlow(false)
    val isLoadingOrder = _isLoadingOrder.asStateFlow()


    //// order
    private val _orderDetails = MutableStateFlow<Map<String, List<GetFoodDetail>>>(emptyMap())
    val orderDetails = _orderDetails.asStateFlow()

    //is loading
    private val _isLoadingOrderDetail = MutableStateFlow(false)
    val isLoadingOrderDetail = _isLoadingOrderDetail.asStateFlow()

    // star
    private val _allFoodStateFlow = MutableStateFlow<List<Food>>(emptyList())

    var orderIdComment by mutableStateOf("")
    var commentSuccess by mutableStateOf<Boolean?>(null)
    private val tag = ViewModel::class.java.simpleName

    init {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val orderDetailsMap = mutableMapOf<String, List<GetFoodDetail>>()
        if (userId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                getOrderByUser(userId)
                _isLoadingOrderDetail.value = false
                _orderFoodStateFlow.value.forEach {
                    val details = getOrderDetail(it.idOrder)
                    if (details != null) {
                        orderDetailsMap[it.idOrder] = details
                    }
                }
                _orderDetails.value = orderDetailsMap
                _isLoadingOrderDetail.value = false
            }
        }
    }

    /*    private fun fetchOrderFood() {
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
                            Log.d(tag, "Get orderfood all success full")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e(
                                tag,
                                "Fetch order confirmation food failed",
                                error.toException()
                            )
                        }
                    })
            }
        }*/

    private suspend fun getOrderByUser(idUser: String) {
        _isLoadingOrder.value = true
        try {
            _orderFoodStateFlow.value = RetrofitClient.orderAPIService.getOrderByUser(idUser)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadingOrder.value = false
        }
    }

    private suspend fun getOrderDetail(idOrder: String): List<GetFoodDetail>? {
        return try {
             RetrofitClient.orderDetailAPIService.getOrderDetails(idOrder)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
            null
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
                        Log.d(tag, "cancel order success")
                    } else {
                        Log.e(tag, "cancel order failed", task.exception)
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
                        Log.d(tag, "foodback order success")
                    } else {
                        Log.e(tag, "foodback order failed", task.exception)
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
                        Log.d(tag, "delivered order success")
                    } else {
                        Log.e(tag, "delivered order failed", task.exception)
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
                        Log.d(tag, "comment order success")
                    } else {
                        Log.e(tag, "comment order failed", task.exception)
                    }
                }
        }
    }

    fun commentFood(comment: Comment) {
        getAllFood()
        // get current time
        val time = Calender().getCalender()
        val commentNew = comment.copy(
            time = time
        )
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val commentRef = FirebaseDatabase.getInstance().getReference("comment")
            commentRef.get().addOnSuccessListener { snapshot ->
                val maxId = snapshot.children.mapNotNull { it.key?.toIntOrNull() }.maxOrNull() ?: 0
                val newId = maxId + 1
                commentRef.child(newId.toString()).setValue(commentNew)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            getNewStarFood(comment = comment)
                            // update star shop
                            getStarShop(comment = comment)
                            Log.d(tag, "Comment successfully")
                            commentSuccess = true

                        } else {
                            commentSuccess = false
                            Log.e(tag, "Comment failed", task.exception)
                        }
                    }
            }.addOnFailureListener { exception ->
                commentSuccess = false
                Log.e(tag, "Failed to comment", exception)
            }
        }

    }

    // Get All food from firebase
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
                    Log.d(
                        tag,
                        "get all food successful in getAllFood"
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        tag,
                        "Failed to fetch all food in fun getAllFood"
                    )
                }

            })
    }

    // calculator new star for food
    private fun getNewStarFood(comment: Comment) {
        var newStar: Float
        _allFoodStateFlow.value.forEach { food ->
            if (comment.idFood == food.Id) {
                newStar = (comment.rating + food.Star.toFloat()) / 2.0f
                Log.d(tag, "set new star: $newStar")
                changeStarFood(star = newStar, idFood = comment.idFood.toString())
            } else {
                Log.e(tag, "get new star failed")
            }
        }
    }

    private fun changeStarFood(star: Float, idFood: String) {
        val newStar = mapOf(
            "Star" to star
        )
        FirebaseDatabase.getInstance().getReference("Foods").child(idFood).updateChildren(newStar)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "update new star success")
                } else {
                    Log.e(tag, "Failed to comment", task.exception)
                }
            }
    }

    private fun changeStarShop(star: Float, idShop: String) {
        val newStar = mapOf(
            "starShop" to star
        )
        FirebaseDatabase.getInstance().getReference("adminprofile").child(idShop)
            .updateChildren(newStar)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(tag, "update new star shop success")
                } else {
                    Log.e(tag, "Failed to comment", task.exception)
                }

            }
    }

    private fun getStarShop(comment: Comment) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("adminprofile").child(comment.idShop)

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val starShop = snapshot.child("starShop").getValue(Float::class.java) ?: 0.0f
                    val newStarShop = (starShop + comment.rating) / 2.0f
                    changeStarShop(star = newStarShop, idShop = comment.idShop)

                    Log.d(tag, "get star success")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(tag, "get star shop failed: ${error.message}")
            }
        })
    }
}
