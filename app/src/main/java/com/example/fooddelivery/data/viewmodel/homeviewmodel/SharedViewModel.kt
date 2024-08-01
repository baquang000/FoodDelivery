package com.example.fooddelivery.data.viewmodel.homeviewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Calender
import com.example.fooddelivery.data.model.Comment
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

    var isErrorDelete by mutableStateOf(false)

    //switch delivery to door
    private val priceTaxandDelivery = MutableStateFlow(0)

    // reward for driver
    private val rewardForDriver = MutableStateFlow(0)

    //save comment value
    private val _commentStateFlow = MutableStateFlow<List<Comment>>(emptyList())
    val commentStateFlow: StateFlow<List<Comment>> = _commentStateFlow.asStateFlow()

    init {
        fetchDiscountCodeFromFirebase()
        getCommentFromFirebase()
    }

    private fun sumPrice() {
        val totalPrice =
            _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }.toFloat()
        val discountvalue = _discountCodeValue.value
        _sumPrice.value = if (discountvalue == 15000f) {
            totalPrice + 15000 - discountvalue + priceTaxandDelivery.value + rewardForDriver.value
        } else {
            totalPrice - totalPrice * discountvalue + 15000 + priceTaxandDelivery.value + rewardForDriver.value
        }
    }

    fun deliverytoDoorChange(witch: Boolean) {
        priceTaxandDelivery.value = if (witch) 8000 else 3000
        sumPrice()
    }

    fun rewardForDriverChange(value: Int) {
        rewardForDriver.value = value
        sumPrice()
    }

    fun getDiscountCodeValue(value: Float) {
        _discountCodeValue.value = value
        sumPrice()
    }

    private fun fetchDiscountCodeFromFirebase() {
        val emptyList = mutableListOf<DiscountCode>()
        discountCode.value = DiscountCodeState.Loading
        FirebaseDatabase.getInstance().getReference("DiscountCode").orderByChild("isshow")
            .equalTo(true)
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


    fun updateFoodDetailQuantity(id: Int, newQuantity: Int) {
        _foodDetailStateFlow.update { list ->
            list.map { food ->
                if (food.id == id) food.copy(quantity = newQuantity)
                else food
            }
        }
        sumPrice()
    }

    fun caculatorPrice(): Double {
        return _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }
    }

    fun addFoodHistoryAndDelete(
        name: String,
        numberphone: String,
        address: String,
        email: String,
        dateofbirth: String,
        noteOrder: String,
        rewardForDriver: Int,
        deliverytoDoor: Boolean,
        diningSubtances: Boolean
    ) {
        val timeOrder = Calender().getCalender()
        val foodList = _foodDetailStateFlow.value
        val orderlist = OrderFood(
            listFood = foodList, sumPrice = _sumPrice.value,
            name = name,
            numberphone = numberphone,
            address = address,
            email = email,
            dateofbirth = dateofbirth,
            time = timeOrder,
            noteOrder = noteOrder,
            rewardForDriver = rewardForDriver,
            deliverytoDoor = deliverytoDoor,
            diningSubtances = diningSubtances
        )
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        orderlist.idUser = userId
        if (userId != null) {
            val order = FirebaseDatabase.getInstance().getReference("orderFood").push()
            val keyOrder = order.key
            orderlist.idOrder = keyOrder!!
            order.setValue(orderlist).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Save success")
                    _foodDetailStateFlow.value = emptyList()
                } else {
                    Log.e("Firebase", "Save failed", task.exception)
                }
            }
        }
    }

    fun deleteItemFoodinCart(foodDetails: FoodDetails) {
        _foodDetailStateFlow.update { list ->
            val updatelist = list.toMutableList()
            val itemToRemove = updatelist.find { it.id == foodDetails.id }
            if (itemToRemove != null) {
                updatelist.remove(itemToRemove)
                isErrorDelete = false
            } else {
                isErrorDelete = true
            }
            updatelist
        }
    }

    //getComment
    private fun getCommentFromFirebase() {
        val templist = mutableListOf<Comment>()
        FirebaseDatabase.getInstance().getReference("comment")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnap in snapshot.children) {
                        val comment = datasnap.getValue(Comment::class.java)
                        if (comment != null) {
                            templist.add(comment)
                        }
                    }
                    _commentStateFlow.value = templist
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "SharedViewModel",
                        "Error fetching comment in fun getCommentFromFirebase: ${error.message}"
                    )
                }

            })
    }
}

