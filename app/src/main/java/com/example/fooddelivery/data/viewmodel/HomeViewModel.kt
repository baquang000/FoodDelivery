package com.example.fooddelivery.data.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeViewModel : ViewModel() {
    val responseBestFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)

    init {
        //fetchAllDataFoodFromFireBase()
        fetchBestFood()
    }

    private fun fetchBestFood() {
        val temList = mutableListOf<Food>()
        responseBestFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("BestFood")
            .equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val foodItem = dataSnap.getValue(Food::class.java)
                        if (foodItem != null) {
                            temList.add(foodItem)
                        }
                    }
                    responseBestFood.value = FoodState.Success(temList)
                }

                override fun onCancelled(error: DatabaseError) {
                    responseBestFood.value = FoodState.Failure(error.message)
                }

            })
    }

    private fun fetchAllDataFoodFromFireBase() {
        val tempList = mutableListOf<Food>()
        responseBestFood.value = FoodState.Loading
        FirebaseDatabase.getInstance().getReference("Foods")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val foodItem = dataSnap.getValue(Food::class.java)
                        if (foodItem != null) {
                            tempList.add(foodItem)
                            Log.e("List", "${tempList.size}")
                        }
                    }
                    responseBestFood.value = FoodState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    responseBestFood.value = FoodState.Failure(error.message)
                }

            })
    }
}





