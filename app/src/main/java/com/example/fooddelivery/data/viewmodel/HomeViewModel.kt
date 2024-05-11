package com.example.fooddelivery.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.example.fooddelivery.data.model.Location
import com.example.fooddelivery.data.model.LocationState
import com.example.fooddelivery.data.model.Price
import com.example.fooddelivery.data.model.PriceState
import com.example.fooddelivery.data.model.Time
import com.example.fooddelivery.data.model.TimeState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeViewModel : ViewModel() {
    val responseBestFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val searchFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val location: MutableState<LocationState> = mutableStateOf(LocationState.Empty)
    val time: MutableState<TimeState> = mutableStateOf(TimeState.Empty)
    val price: MutableState<PriceState> = mutableStateOf(PriceState.Empty)

    init {
        fetchAllDataFoodFromFireBase()
        fetchBestFood()
        fetchLocationFromFirebase()
        fetchTimeFromFirebase()
        fetchPriceFromFirebase()
    }

    private fun fetchPriceFromFirebase() {
        val emptyList = mutableListOf<Price>()
        price.value = PriceState.Loading
        FirebaseDatabase.getInstance().getReference("Price")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val priceList = dataSnap.getValue(Price::class.java)
                        if (priceList != null) {
                            emptyList.add(priceList)
                        }
                    }
                    price.value = PriceState.Success(emptyList)
                }

                override fun onCancelled(error: DatabaseError) {
                    price.value = PriceState.Failure(error.message)
                }
            })
    }

    private fun fetchTimeFromFirebase() {
        val emptyList = mutableListOf<Time>()
        time.value = TimeState.Loading
        FirebaseDatabase.getInstance().getReference("Time")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val timeList = dataSnap.getValue(Time::class.java)
                        if (timeList != null) {
                            emptyList.add(timeList)
                        }
                    }
                    time.value = TimeState.Success(emptyList)
                }

                override fun onCancelled(error: DatabaseError) {
                    time.value = TimeState.Failure(error.message)
                }
            })
    }

    private fun fetchLocationFromFirebase() {
        val emptyList = mutableListOf<Location>()
        location.value = LocationState.Loading
        FirebaseDatabase.getInstance().getReference("Location")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val locList = dataSnap.getValue(Location::class.java)
                        if (locList != null) {
                            emptyList.add(locList)
                        }
                    }
                    location.value = LocationState.Success(emptyList)
                }

                override fun onCancelled(error: DatabaseError) {
                    location.value = LocationState.Failure(error.message)
                }
            })
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
        searchFood.value = FoodState.Loading
        FirebaseDatabase.getInstance().getReference("Foods")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val foodItem = dataSnap.getValue(Food::class.java)
                        if (foodItem != null) {
                            tempList.add(foodItem)
                        }
                    }
                    searchFood.value = FoodState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    searchFood.value = FoodState.Failure(error.message)
                }
            })
    }
}





