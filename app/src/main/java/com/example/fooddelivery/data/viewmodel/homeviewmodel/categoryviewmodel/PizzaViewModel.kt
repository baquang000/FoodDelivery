package com.example.fooddelivery.data.viewmodel.homeviewmodel.categoryviewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PizzaViewModel : ViewModel() {
    val pizzaFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)

    init {
        petchPizzFood()
    }

    private fun petchPizzFood() {
        val emptyList = mutableListOf<Food>()
        pizzaFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(0.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                pizzaFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                pizzaFood.value = FoodState.Failure(error.message)
            }

        })
    }
}

