package com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.FoodDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HistoryFoodViewModel : ViewModel() {
    private val _historyFoodStateFlow = MutableStateFlow<List<List<FoodDetails>>>(emptyList())
    val historyFoodStateFlow = _historyFoodStateFlow.asStateFlow()
    init {
        fetchHistoryFood()
    }

    private fun fetchHistoryFood() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("historyFood").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val historyList = mutableListOf<List<FoodDetails>>()
                        for (datasnap in snapshot.children) {
                            val foodlist = datasnap.getValue(object :
                                GenericTypeIndicator<List<FoodDetails>>() {})
                            if (foodlist != null) {
                                historyList.add(foodlist)
                            }
                        }
                        _historyFoodStateFlow.value = historyList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Firebase", "Fetch history failed", error.toException())
                    }

                })
        }
    }
}