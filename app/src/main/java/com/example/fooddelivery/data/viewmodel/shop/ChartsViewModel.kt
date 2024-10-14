package com.example.fooddelivery.data.viewmodel.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.ChartCount
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class ChartsViewModel : ViewModel() {
    private val _chartCount = MutableStateFlow<ChartCount?>(null)
    val chartCount = _chartCount.asStateFlow()

    private val _loadingChartsCount = MutableStateFlow(false)
    val loadingChartsCount = _loadingChartsCount.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getChartCount()
        }
    }

    private suspend fun getChartCount() {
        _loadingChartsCount.value = true
        try {
            _chartCount.value = RetrofitClient.chartsAPIService.getChartsByShop(ID)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _loadingChartsCount.value = false
        }
    }
}