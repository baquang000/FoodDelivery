package com.example.fooddelivery.data.viewmodel.shop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Calender
import com.example.fooddelivery.data.model.GetOrderItem
import com.example.fooddelivery.data.model.OrderStatus
import com.example.fooddelivery.data.model.UpdateOrder
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.untils.SocketManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    //////////////get data from api
    //// order
    private val _orderStateFlow = MutableStateFlow<List<GetOrderItem>>(emptyList())
    val orderStateFlow = _orderStateFlow.asStateFlow()

    //is loading
    private val _isLoadingOrder = MutableStateFlow(false)
    val isLoadingOrder = _isLoadingOrder.asStateFlow()

    private val tag = ViewModel::class.java.simpleName

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice = _totalPrice.asStateFlow()

    var numIsConfirm = 0
    var deliveringOrder = 0
    var countdeliveredOrder = 0
    var cancelOrder = 0

    private val _isOrderFlow = MutableStateFlow(false) // Initial value is `false`


    private val _isUpdateStatusOrder = MutableStateFlow(false) // Initial value is `false`


    init {
        if (ID != 0) {
            viewModelScope.launch(Dispatchers.IO) {
                getOrderByUser(ID)
            }
        }
        SocketManager.onOrderReceived { isOrder ->
            _isOrderFlow.value = isOrder
            if (isOrder) {
                viewModelScope.launch(Dispatchers.IO) {
                    getOrderByUser(ID)
                    _isOrderFlow.value = false
                }
            }
        }
        SocketManager.onUpdateStatusOrder { isOrder ->
            _isUpdateStatusOrder.value = isOrder
            if (isOrder) {
                viewModelScope.launch(Dispatchers.IO) {
                    getOrderByUser(ID)
                    _isUpdateStatusOrder.value = false
                }
            }
        }
    }


    private suspend fun getOrderByUser(idShop: Int) {
        _isLoadingOrder.value = true
        try {
            _orderStateFlow.value = RetrofitClient.orderAPIService.getOrderByShop(idShop)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadingOrder.value = false
            updateOrderCounts()
        }
    }

    suspend fun updateStatusWithApi(idOrder: Int, orderStatus: String) {
        if (ID != 0) {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    val response = RetrofitClient.orderAPIService.updateOrder(
                        id = idOrder,
                        statusOrder = UpdateOrder(
                            orderStatus = orderStatus,
                        ),

                        )
                    if (response.isSuccessful) {
                        updateOrderStateFlow(idOrder = idOrder, newStatus = orderStatus)
                        updateOrderCounts()
                        SocketManager.emitUpdateStatusOrder(true)
                    } else {
                        Log.e(
                            tag,
                            "Failed to update order status: ${response.errorBody()?.string()}"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

    private fun updateOrderStateFlow(idOrder: Int, newStatus: String) {
        val updatedOrderList = _orderStateFlow.value.map { orderItem ->
            if (orderItem.id == idOrder) {
                // If the order matches the ID, return a copy with the updated status
                orderItem.copy(orderStatus = newStatus)
            } else {
                // Otherwise, return the order as-is
                orderItem
            }
        }

        // Update the state flow with the new list of orders
        _orderStateFlow.value = updatedOrderList
    }

    fun updateOrderCounts() {
        var newIsConfirm = 0
        var newDelivering = 0
        var newDelivered = 0
        var newCancel = 0
        var totalPrice = 0.0
        _orderStateFlow.value.forEach { order ->
            when (order.orderStatus) {
                OrderStatus.PENDING.toString() -> {
                    newIsConfirm++
                }

                OrderStatus.DELIVERY.toString() -> {
                    newDelivering++
                }

                OrderStatus.SUCCESS.toString() -> {
                    totalPrice += order.totalMoney.toFloat()
                    newDelivered++
                }

                OrderStatus.CANCEL.toString() -> {
                    newCancel++
                }

                OrderStatus.FOODBACK.toString() -> {
                    totalPrice += order.totalMoney.toFloat()
                    newDelivered++
                }
            }
        }
        numIsConfirm = newIsConfirm
        deliveringOrder = newDelivering
        countdeliveredOrder = newDelivered
        cancelOrder = newCancel
        _totalPrice.value = totalPrice
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        ID = 0
    }
}