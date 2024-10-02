package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Location
import com.example.fooddelivery.data.model.LocationState
import com.example.fooddelivery.data.model.Price
import com.example.fooddelivery.data.model.PriceState
import com.example.fooddelivery.data.model.Time
import com.example.fooddelivery.data.model.TimeState
import com.example.fooddelivery.data.model.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    val location: MutableState<LocationState> = mutableStateOf(LocationState.Empty)
    val time: MutableState<TimeState> = mutableStateOf(TimeState.Empty)
    val price: MutableState<PriceState> = mutableStateOf(PriceState.Empty)

    //Best food
    private val _bestFood = MutableStateFlow<List<Food>>(emptyList())
    val bestFood = _bestFood.asStateFlow()
    private val _isLoadBestFood = MutableStateFlow(false)
    val isLoadBestFood = _isLoadBestFood.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        fetchLocationFromFirebase()
        fetchTimeFromFirebase()
        fetchPriceFromFirebase()
        viewModelScope.launch(Dispatchers.IO) {
            getBestFoodWithApi()
        }
    }

    private suspend fun getBestFoodWithApi() {
        _isLoadBestFood.value = true
        try {
            _bestFood.value = RetrofitClient.foodAPIService.getBestFood()
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadBestFood.value = false
        }
    }



    //////
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


    /////////
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

    ///////////
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
}





