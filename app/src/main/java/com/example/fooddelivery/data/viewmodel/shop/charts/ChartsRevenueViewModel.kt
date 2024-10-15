package com.example.fooddelivery.data.viewmodel.shop.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.ChartCount
import com.example.fooddelivery.data.model.ChartOrder
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class ChartsRevenueViewModel : ViewModel() {
    private val _chartRevenue = MutableStateFlow<ChartOrder?>(null)
    val chartRevenue = _chartRevenue.asStateFlow()

    private val _loadingChartRevenue = MutableStateFlow(false)
    val loadingChartRevenue = _loadingChartRevenue.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getChartRevenue()
        }
    }

    private suspend fun getChartRevenue() {
        _loadingChartRevenue.value = true
        try {
            _chartRevenue.value =
                RetrofitClient.chartsAPIService.getChartRevenueByShop(ID)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _loadingChartRevenue.value = false
        }
    }
}