package com.example.fooddelivery.data.viewmodel.shop.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.ChartOrder
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class ChartsOrderViewModel : ViewModel() {
    private val _chartOrder = MutableStateFlow<ChartOrder?>(null)
    val chartOrder = _chartOrder.asStateFlow()

    private val _loadingChartOrder = MutableStateFlow(false)
    val loadingChartsOrder = _loadingChartOrder.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getChartOrder()
        }
    }

    private suspend fun getChartOrder() {
        _loadingChartOrder.value = true
        try {
            _chartOrder.value =
                RetrofitClient.chartsAPIService.getChartOrderByShop(ID)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _loadingChartOrder.value = false
        }
    }
}