package com.example.fooddelivery.view.shop.home.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.shop.FoodItem
import com.example.fooddelivery.components.shop.NormalTextComponents
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.viewmodel.shop.ViewAllViewModel

@Composable
fun ViewAllScreen(
    navController: NavController,
    viewAllViewModel: ViewAllViewModel = viewModel(),
) {
    ///get all food
    val allFood by viewAllViewModel.allFood.collectAsStateWithLifecycle()
    val isLoadingFood by viewAllViewModel.isLoadAllFood.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = allFood) {
        viewAllViewModel.getAllFoodWithApi()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow_icon
                    ),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                NormalTextComponents(
                    value = stringResource(id = R.string.view_all),
                    nomalColor = colorResource(id = R.color.black),
                    nomalFontsize = 28.sp,
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        }
        GetAllFood(
            data = allFood,
            isload = isLoadingFood,
            navController = navController,
            viewAllViewModel = viewAllViewModel
        )
    }
}

@Composable
fun GetAllFood(
    data: List<Food>,
    isload: Boolean,
    navController: NavController,
    viewAllViewModel: ViewAllViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isload) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(data) { food ->
                    FoodItem(
                        food = food,
                        navController = navController,
                        viewAllViewModel = viewAllViewModel
                    )
                }
            }
        }
    }
}
