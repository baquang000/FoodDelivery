package com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.Calender
import com.example.fooddelivery.data.model.CreateComment
import com.example.fooddelivery.data.model.GetOrderItem
import com.example.fooddelivery.data.model.OrderStatus
import com.example.fooddelivery.data.model.UpdateOrder
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.untils.SocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderFoodViewModel : ViewModel() {
    //// order
    private val _orderFoodStateFlow = MutableStateFlow<List<GetOrderItem>>(emptyList())
    val orderFoodStateFlow = _orderFoodStateFlow.asStateFlow()

    //is loading
    private val _isLoadingOrder = MutableStateFlow(false)
    val isLoadingOrder = _isLoadingOrder.asStateFlow()


    var commentSuccess by mutableStateOf<Boolean?>(null)

    private val _idOrder = MutableStateFlow(0)
    val idOrder = _idOrder.asStateFlow()

    private val tag = ViewModel::class.java.simpleName

    private val _isUpdateStatusOrder = MutableStateFlow(false) // Initial value is `false`


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getOrderByUser()
        }
        SocketManager.onUpdateStatusOrder { isOrder ->
            _isUpdateStatusOrder.value = isOrder
            if (isOrder) {
                viewModelScope.launch(Dispatchers.IO) {
                    getOrderByUser()
                    _isUpdateStatusOrder.value = false
                }
            }
        }
    }


    suspend fun getOrderByUser() {
        if (ID != 0) {
            _isLoadingOrder.value = true
            try {
                _orderFoodStateFlow.value = RetrofitClient.orderAPIService.getOrderByUser(ID)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadingOrder.value = false
            }
        }
    }

    suspend fun updateStatusWithApi(idOrder: Int, orderStatus: String) {
        val time = Calender().getCalender()
        if (ID != 0) {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    val response = RetrofitClient.orderAPIService.updateOrderByUser(
                        idUser = ID,
                        id = idOrder,
                        statusOrder = UpdateOrder(
                            orderStatus = orderStatus,
                            time = time
                        )
                    )
                    if (response.isSuccessful) {
                        updateOrderStateFlow(idOrder = idOrder, newStatus = orderStatus)
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
        val updatedOrderList = _orderFoodStateFlow.value.map { orderItem ->
            if (orderItem.id == idOrder) {
                // If the order matches the ID, return a copy with the updated status
                orderItem.copy(orderStatus = newStatus)
            } else {
                // Otherwise, return the order as-is
                orderItem
            }
        }

        // Update the state flow with the new list of orders
        _orderFoodStateFlow.value = updatedOrderList
    }

    ///////////// comment
    fun setIdOrder(idOrder: Int) {
        _idOrder.value = idOrder
    }

    fun commentFood(comment: CreateComment) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = RetrofitClient.commentAPIService.createComment(comment)
                if (response.isSuccessful) {
                    updateStatusWithApi(
                        idOrder = comment.idOrder,
                        orderStatus = OrderStatus.FOODBACK.toString()
                    )
                } else {
                    Log.e(
                        tag,
                        "Comment is failed : ${response.errorBody()?.string()}"
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }
    }
}
