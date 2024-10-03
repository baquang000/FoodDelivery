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

    private val _idOrder = MutableStateFlow("")
    val idOrder = _idOrder.asStateFlow()

    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getOrderByUser()
        }
    }


    suspend fun getOrderByUser() {
        if (ID != "") {
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

    suspend fun updateStatusWithApi(idOrder: String, orderStatus: UpdateOrder) {
        if (ID != "") {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    val response = RetrofitClient.orderAPIService.updateOrderStatus(
                        idShop = ID,
                        idOrder = idOrder,
                        statusOrder = orderStatus
                    )
                    if (response.isSuccessful) {
                        updateOrderStateFlow(idOrder = idOrder, newStatus = orderStatus.statusOrder)
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

    private fun updateOrderStateFlow(idOrder: String, newStatus: String) {
        val updatedOrderList = _orderFoodStateFlow.value.map { orderItem ->
            if (orderItem.idOrder == idOrder) {
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
    fun setIdOrder(idOrder: String) {
        _idOrder.value = idOrder
    }

    fun commentFood(comment: CreateComment) {
        val time = Calender().getCalender()
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = RetrofitClient.commentAPIService.createComment(comment)
                if (response.isSuccessful) {
                    updateStatusWithApi(
                        idOrder = comment.idOrder,
                        orderStatus = UpdateOrder(OrderStatus.FOODBACK.toString(),time),
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
