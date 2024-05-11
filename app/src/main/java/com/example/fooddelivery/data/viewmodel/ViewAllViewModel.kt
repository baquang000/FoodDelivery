package com.example.fooddelivery.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewAllViewModel : ViewModel() {
    val allFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)

    init {
        fetchAllDataFood()
    }

    private fun fetchAllDataFood() {
        val empList = mutableListOf<Food>()
        allFood.value = FoodState.Loading
        FirebaseDatabase.getInstance().getReference("Foods")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val foodItem = dataSnap.getValue(Food::class.java)
                        if (foodItem != null) {
                            empList.add(foodItem)
                        }
                    }
                    allFood.value = FoodState.Success(empList)
                }

                override fun onCancelled(error: DatabaseError) {
                    allFood.value = FoodState.Failure(error.message)
                }
            })
    }
}