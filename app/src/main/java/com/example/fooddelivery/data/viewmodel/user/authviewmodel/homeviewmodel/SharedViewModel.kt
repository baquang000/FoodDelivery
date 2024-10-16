package com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.CreateOrder
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.model.GetDiscountItem
import com.example.fooddelivery.data.model.TypeDiscount
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.data.viewmodel.Token
import com.example.fooddelivery.untils.SocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class SharedViewModel : ViewModel() {
    private val _foodDetailStateFlow = MutableStateFlow<List<FoodDetails>>(emptyList())
    val foodDetailStateFlow: StateFlow<List<FoodDetails>> = _foodDetailStateFlow.asStateFlow()


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

    private var _priceDiscount = MutableStateFlow(0f)
    var priceDiscount: StateFlow<Float> = _priceDiscount.asStateFlow()

    //// update order when createOrderAndDetails
    private val _isUpdateOrder = MutableStateFlow(false)
    val isUpdateOrder = _isUpdateOrder.asStateFlow()

    private var discountCode by mutableStateOf<GetDiscountItem?>(null)


    fun sendDiscount(discount: GetDiscountItem) {
        discountCode = discount
        sumPrice()
    }

    fun notChooseDiscount(option: Int, discount: GetDiscountItem) {
        if (option == 3) {
            discountCode = discount
            sumPrice()
        }
    }

    private fun sumPrice() {
        _totalPrice.value =
            _foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }.toFloat()
        if (discountCode != null) {
            if (discountCode!!.typeDiscount == TypeDiscount.PERCENTAGE.toString()) {
                _priceDiscount.value = if (
                    (_totalPrice.value * (discountCode!!.percentage.toFloat() / 100)) >= discountCode!!.maxDiscountAmount.toFloat()
                ) {
                    discountCode!!.maxDiscountAmount.toFloat()
                } else {
                    (_totalPrice.value + priceTaxandDelivery.value + rewardForDriver.value + 15000) * (discountCode!!.percentage.toFloat() / 100)
                }
                _sumPrice.value =
                    _totalPrice.value - _priceDiscount.value + priceTaxandDelivery.value + rewardForDriver.value + 15000
            } else {
                _priceDiscount.value = discountCode!!.percentage.toFloat()
                _sumPrice.value =
                    _totalPrice.value - _priceDiscount.value + priceTaxandDelivery.value + rewardForDriver.value + 15000
            }
        } else {
            _sumPrice.value =
                _totalPrice.value + priceTaxandDelivery.value + rewardForDriver.value + 15000
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
        diningSubtances: Boolean,
    ) {
        if (ID != 0) {

            val newOrder = CreateOrder(
                deliverytoDoor = deliverytoDoor,
                diningSubtances = diningSubtances,
                idShop = _idShopStateFlow.value,
                idUser = ID,
                noteOrder = noteOrder,
                rewardForDriver = rewardForDriver,
                totalMoney = _sumPrice.value.toString(),
                orderDetails = _foodDetailStateFlow.value,
                idDiscount = discountCode?.id ?: 3
            )
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitClient.orderAPIService.createOrder(Token, newOrder)
                    if (response.isSuccessful) {
                        SocketManager.emitOrder(isOrder = true)
                        deleteListFood()
                    }
                } catch (e: Exception) {
                    throw e
                    e.printStackTrace()
                } finally {
                    _isUpdateOrder.value = !_isUpdateOrder.value
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
        _countFoodInCart.value = _foodDetailStateFlow.value.size
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

