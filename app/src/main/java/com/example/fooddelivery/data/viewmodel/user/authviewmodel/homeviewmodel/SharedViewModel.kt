package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Calender
import com.example.fooddelivery.data.model.CreateOrder
import com.example.fooddelivery.data.model.DiscountCode
import com.example.fooddelivery.data.model.DiscountCodeState
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.untils.SocketManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    private var _countFoodInCart =
        MutableStateFlow(0)
    var countFoodInCart: StateFlow<Int> = _countFoodInCart.asStateFlow()

    private var _totalPrice = MutableStateFlow(0f)
    var totalPrice: StateFlow<Float> = _totalPrice.asStateFlow()

    private val _idShopStateFlow = MutableStateFlow(0)
    val idShopStateFlow = _idShopStateFlow.asStateFlow()

    private val _nameShop = MutableStateFlow("")
    val nameShop: StateFlow<String> = _nameShop.asStateFlow()

    private val tag = ViewModel::class.java.simpleName


    //// update order when createOrderAndDetails
    private val _isUpdateOrder = MutableStateFlow(false)
    val isUpdateOrder = _isUpdateOrder.asStateFlow()

    init {
        fetchDiscountCodeFromFirebase()
    }

    private fun sumPrice() {
        _totalPrice.value =
            _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }.toFloat()
        val discountvalue = _discountCodeValue.value
        _sumPrice.value = if (discountvalue == 15000f) {
            _totalPrice.value + 15000 - discountvalue + priceTaxandDelivery.value + rewardForDriver.value
        } else {
            _totalPrice.value - _totalPrice.value * discountvalue + 15000 + priceTaxandDelivery.value + rewardForDriver.value
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

    ///////////////
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
            _countFoodInCart.value++
            return@update updatedList.toList()
        }
        sumPrice()
    }

    fun deleteFoodDetail(foodDetails: FoodDetails) {
        _foodDetailStateFlow.update { currentList ->
            val updatedList = currentList.toMutableList()
            val existingItem = updatedList.find { it.title == foodDetails.title }
            if (existingItem != null) {
                existingItem.quantity -= foodDetails.quantity
            } else {
                Log.e(tag, "Not found fooddetails in _foodDetailStateFlow and delete failed")
            }
            updatedList.removeIf { it.idFood == foodDetails.idFood && it.quantity == 0 }
            _countFoodInCart.value--
            return@update updatedList.toList()
        }
        sumPrice()
    }

    fun updateFoodDetailQuantity(id: Int, newQuantity: Int) {
        _foodDetailStateFlow.update { list ->
            list.map { food ->
                if (food.idFood == id) food.copy(quantity = newQuantity)
                else food
            }
        }
        sumPrice()
    }

    fun caculatorPrice(): Double {
        return _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }
    }

    fun createOrderAndDetails(
        noteOrder: String,
        rewardForDriver: Int,
        deliverytoDoor: Boolean,
        diningSubtances: Boolean
    ) {
        val timeOrder = Calender().getCalender()
        if (ID != 0) {
            val newOrder = CreateOrder(
                deliverytoDoor = deliverytoDoor,
                diningSubtances = diningSubtances,
                idShop = _idShopStateFlow.value,
                idUser = ID,
                noteOrder = noteOrder,
                rewardForDriver = rewardForDriver,
                sumPrice = _sumPrice.value.toDouble(),
                time = timeOrder,
                orderDetails = _foodDetailStateFlow.value
            )
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitClient.orderAPIService.createOrder(newOrder)
                    if (response.isSuccessful) {
                        SocketManager.emitOrder(isOrder = true)
                    }
                } catch (e: Exception) {
                    Log.e(tag, e.message.toString())
                } finally {
                    _isUpdateOrder.value = !_isUpdateOrder.value
                    deleteListFood()
                }
            }
        }
    }

    fun deleteItemFoodInCart(foodDetails: FoodDetails) {
        _foodDetailStateFlow.update { list ->
            val updatelist = list.toMutableList()
            val itemToRemove = updatelist.find { it.idFood == foodDetails.idFood }
            if (itemToRemove != null) {
                updatelist.remove(itemToRemove)
                isErrorDelete = false
            } else {
                isErrorDelete = true
            }
            updatelist
        }
    }

    fun getIdShop(id: Int) {
        if (_idShopStateFlow.value == 0)
            _idShopStateFlow.value = id
        else {
            if (_idShopStateFlow.value != id) {
                deleteListFood()
            }
        }
    }

    fun getNameShop(name: String?) {
        _nameShop.value = name ?: ""
    }

    fun deleteListFood() {
        _foodDetailStateFlow.value = emptyList()
        _countFoodInCart.value = 0
        _nameShop.value = ""
        _totalPrice.value = 0f
        _idShopStateFlow.value = 0
    }

}

