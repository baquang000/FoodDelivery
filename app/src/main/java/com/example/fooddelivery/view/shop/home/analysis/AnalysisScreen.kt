package com.example.fooddelivery.view.shop.home.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.components.charts.PieChart
import com.example.fooddelivery.data.viewmodel.shop.ChartsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    navController: NavController,
    chartsViewModel: ChartsViewModel = viewModel()
) {
    val loading by chartsViewModel.loadingChartsCount.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.statistical),
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
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    item {
                        HeadingAnalysis()
                    }
                    item {
                        NormalTextComponents(
                            value = "Thống kê tổng các phần",
                            nomalColor = Color.Black,
                            nomalFontsize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                            nomalFontWeight = FontWeight.Bold
                        )
                    }
                    item {
                        AnalysisItem(chartsViewModel = chartsViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun HeadingAnalysis() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.LightGray.copy(alpha = 0.3f)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu), contentDescription = null,
                tint = Color.Unspecified, modifier = Modifier.size(56.dp)
            )
            Text(text = stringResource(R.string.food), color = Color.Black, fontSize = 14.sp)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.basket), contentDescription = null,
                tint = Color.Unspecified, modifier = Modifier.size(56.dp)
            )
            Text(
                text = stringResource(id = R.string.order_screen),
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun AnalysisItem(chartsViewModel: ChartsViewModel) {
    val chartCount by chartsViewModel.chartCount.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Preview with sample data
        if (chartCount != null) {
            PieChart(
                data = chartCount!!
            )
        }
    }
}

//@Preview(
//    showSystemUi = true
//)
//@Composable
//fun AnalysisScreenPreview() {
//    AnalysisScreen()
//}
