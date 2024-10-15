package com.example.fooddelivery.view.shop.home.analysis

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
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.viewmodel.shop.charts.ChartsFoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodAnalysisScreen(
    navController: NavController,
    chartsFoodViewModel: ChartsFoodViewModel = viewModel()
) {
    val loading by chartsFoodViewModel.isLoadAllFood.collectAsStateWithLifecycle()
    val allFood by chartsFoodViewModel.allFood.collectAsStateWithLifecycle()
    val bestFood = allFood.maxByOrNull { it.sold }
    val allBestFood = allFood.filter { it.bestFood }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.analysis_food),
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
                        if (bestFood != null) {
                            AnalysisBestFood(bestFood = bestFood)
                        }
                    }
                    item {
                        AnalysisDetailFood(allBestFood = allBestFood)
                    }
                }
            }
        }
    }
}

@Composable
fun AnalysisBestFood(bestFood: Food) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        NormalTextComponents(
            value = "Đồ ăn đươc bán nhiều nhất", nomalColor = Color.Black,
            nomalFontsize = 20.sp,
            nomalFontWeight = FontWeight.Bold
        )
        FoodDetailInfo(food = bestFood)
        NormalTextComponents(
            value = "Đã bán được : ${bestFood.sold}",
            nomalColor = Color.Black,
            nomalFontsize = 20.sp
        )
    }
}

@Composable
fun FoodDetailInfo(food: Food) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = food.imagePath, contentDescription = food.title,
            modifier = Modifier.size(100.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NormalTextComponents(
                value = food.title,
                nomalColor = Color.Black,
                nomalFontsize = 16.sp
            )
            NormalTextComponents(
                value = "${food.price.price}đ",
                nomalColor = Color.Black,
                nomalFontsize = 16.sp
            )
        }
    }
}

@Composable
fun AnalysisDetailFood(allBestFood: List<Food>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        NormalTextComponents(
            value = "Đồ ăn tốt nhất của cửa hàng", nomalColor = Color.Black,
            nomalFontsize = 20.sp,
            nomalFontWeight = FontWeight.Bold
        )
        allBestFood.forEach { food ->
            FoodDetailInfo(food)
        }
    }
}