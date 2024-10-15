package com.example.fooddelivery.view.shop.home.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.charts.CustomBar
import com.example.fooddelivery.data.model.ChartOrder
import com.example.fooddelivery.data.viewmodel.shop.charts.ChartsRevenueViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueAnalysisScreen(
    navController: NavController,
    chartsRevenueViewModel: ChartsRevenueViewModel = viewModel()
) {
    val loading by chartsRevenueViewModel.loadingChartRevenue.collectAsStateWithLifecycle()
    val chartRevenue by chartsRevenueViewModel.chartRevenue.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.revenue_analysis),
                        color = Color.White,
                        modifier = Modifier.padding(start = 45.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    item {
                        Box(
                            modifier = Modifier.padding(vertical = 12.dp)
                        ) {
                            BarChartScreen(
                                chartRevenue = chartRevenue
                            )
                        }
                    }
                    item {
                        AnalysisDetailRevenue(chartRevenue = chartRevenue)
                    }
                }
            }
        }
    }
}

@Composable
fun AnalysisDetailRevenue(chartRevenue: ChartOrder?) {
    val sum = chartRevenue?.series?.sum()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Tổng doanh thu: ${sum}đ",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BarChartScreen(chartRevenue: ChartOrder?) {

    val values = chartRevenue?.series?.toMutableList()
    val total = values?.sum() ?: 0

    // Tạo list mới chứa tỉ lệ phần trăm của mỗi giá trị
    val percentages = values?.map { value ->
        (value * 100.0) / total
    } ?: emptyList()
    val max = percentages.maxOrNull() ?: 0
    val labels = chartRevenue?.labels?.toList()
    Column(
        modifier = Modifier.background(color = Color.LightGray.copy(0.4f))
    ) {
        Row(
            modifier = Modifier.height(300.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            percentages.forEachIndexed { index, value ->
                CustomBar(
                    size = value.dp,
                    max = max.toFloat(),
                    value = (values?.getOrNull(index) ?: 0.0).toFloat()
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            labels?.forEach { lable ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(10.dp), contentAlignment = Alignment.Center
                ) {
                    Text(text = lable.toString())
                }
            }
        }
    }

}

