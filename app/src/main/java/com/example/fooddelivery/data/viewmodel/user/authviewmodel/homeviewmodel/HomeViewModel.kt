package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.GetCategory
import com.example.fooddelivery.data.model.Price
import com.example.fooddelivery.data.model.Time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    /*  val location: MutableState<LocationState> = mutableStateOf(LocationState.Empty)
     */

    //Best food
    private val _bestFood = MutableStateFlow<List<Food>>(emptyList())
    val bestFood = _bestFood.asStateFlow()
    private val _isLoadBestFood = MutableStateFlow(false)
    val isLoadBestFood = _isLoadBestFood.asStateFlow()


    ///sold Food
    private val _soldFood = MutableStateFlow<List<Food>>(emptyList())
    val soldFood = _soldFood.asStateFlow()
    private val _isLoadSoldFood = MutableStateFlow(false)
    val isLoadSoldFood = _isLoadSoldFood.asStateFlow()

    ///price
    private val _price = MutableStateFlow<List<Price>>(emptyList())
    val price = _price.asStateFlow()
    private val _isLoadPrice = MutableStateFlow(false)
    val isLoadPrice = _isLoadPrice.asStateFlow()

    //time
    private val _time = MutableStateFlow<List<Time>>(emptyList())
    val time = _time.asStateFlow()
    private val _isLoadTime = MutableStateFlow(false)
    val isLoadTime = _isLoadTime.asStateFlow()

    ///category
    private val _category = MutableStateFlow<List<GetCategory>>(emptyList())
    val category = _category.asStateFlow()
    private val _isLoadCategory = MutableStateFlow(false)
    val isLoadCategory = _isLoadCategory.asStateFlow()

    ///tag
    private val tag = ViewModel::class.java.simpleName

    init {
        /*   fetchLocationFromFirebase()*/
        viewModelScope.launch(Dispatchers.IO) {
            getPriceWithApi()
            getTimeWithApi()
            getBestFoodWithApi()
            getCategoryWithApi()
            getSoldFoodWithApi()
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

    private suspend fun getSoldFoodWithApi() {
        _isLoadSoldFood.value = true
        try {
            _soldFood.value = RetrofitClient.foodAPIService.getSoldFood()
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadSoldFood.value = false
        }
    }


    private suspend fun getPriceWithApi() {
        _isLoadPrice.value = true
        try {
            _price.value = RetrofitClient.priceAPIService.getPrice()
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadPrice.value = false
        }
    }


    private suspend fun getTimeWithApi() {
        _isLoadTime.value = true
        try {
            _time.value = RetrofitClient.timeAPIService.getTime()
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadTime.value = false
        }
    }

    private suspend fun getCategoryWithApi() {
        _isLoadCategory.value = true
        try {
            _category.value = RetrofitClient.categoryAPIService.getCategory()
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadCategory.value = false
        }
    }

    /*
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
        }*/
}





