package com.example.fooddelivery.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.FoodItem
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.homeviewmodel.ViewAllViewModel

@Composable
fun ViewAllScreen(
    navController: NavController,
    viewAllViewModel: ViewAllViewModel = viewModel(),
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(innerPaddingValues)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    )
                )
            }
        }
        ViewAllFoodItem(
            viewAllViewModel = viewAllViewModel,
            navController = navController,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun ViewAllFoodItem(
    viewAllViewModel: ViewAllViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    when (val result = viewAllViewModel.allFood.value) {
        is FoodState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FoodState.Success -> {
            GetAllFood(
                result.data,
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }

        is FoodState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = result.message, fontSize = 20.sp)
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.error_loading_data),
                    fontSize = 20.sp,
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
fun GetAllFood(
    data: MutableList<Food>,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(data) { food ->
            FoodItem(
                food = food,
                navController = navController,
                buttonSize = 16.sp,
                spacerbuttonModifier = Modifier.padding(start = 4.dp),
                sharedViewModel = sharedViewModel
            )
        }
    }
}
