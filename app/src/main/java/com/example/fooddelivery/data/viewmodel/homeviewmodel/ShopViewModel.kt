package com.example.fooddelivery.data.viewmodel.homeviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.Shop
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopViewModel : ViewModel() {
    private val _shopProfileStateFlow = MutableStateFlow<List<Shop>>(emptyList())
    val shopProfileStateFlow: StateFlow<List<Shop>> = _shopProfileStateFlow.asStateFlow()
    private val tag = ViewModel::class.java.simpleName
    private val _getFoodStateFlow = MutableStateFlow<List<Food>>(emptyList())
    val getFoodStateFlow: StateFlow<List<Food>> = _getFoodStateFlow.asStateFlow()

    init {
        getShopProfile()
        fetchAllDataFood()
    }

    private fun getShopProfile() {
        val tempList = mutableListOf<Shop>()
        FirebaseDatabase.getInstance().getReference("adminprofile").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnap in snapshot.children) {
                        val shop = datasnap.getValue(Shop::class.java)
                        if (shop != null) {
                            tempList.add(shop)
                        }
                    }
                    _shopProfileStateFlow.value = tempList
                    Log.d(
                        tag,
                        "getShopProfile success"
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        tag,
                        "Error fetching shop profile in fun getShopProfile: ${error.message}"
                    )
                }
            })
    }

    private fun fetchAllDataFood() {
        val empList = mutableListOf<Food>()
        FirebaseDatabase.getInstance().getReference("Foods")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val foodItem = dataSnap.getValue(Food::class.java)
                        if (foodItem != null) {
                            empList.add(foodItem)
                        }
                    }
                    _getFoodStateFlow.value = empList
                    Log.d(
                        tag,
                        "fetchAllDataFood success"
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        tag,
                        "Error fetching food in fun fetchAllDataFood: ${error.message}"
                    )
                }
            })
    }

}