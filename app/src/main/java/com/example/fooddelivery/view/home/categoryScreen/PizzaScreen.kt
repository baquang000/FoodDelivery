package com.example.fooddelivery.view.home.categoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.FoodItem
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.example.fooddelivery.data.viewmodel.homeviewmodel.CategoryViewModel
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel

@Composable
fun PizzaScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel = viewModel(),
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
    ) {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = stringResource(R.string.arrow)
            )
        }
        SetPizzaItem(
            categoryViewModel = categoryViewModel,
            navController = navController,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun SetPizzaItem(
    categoryViewModel: CategoryViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    when (val result = categoryViewModel.pizzaFood.value) {
        is FoodState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is FoodState.Success -> {
            ListCategoryFood(
                result.data,
                navController = navController,
                sharedViewModel = sharedViewModel
            )

        }

        is FoodState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = result.message, style = TextStyle(
                        fontSize = 20.sp,
                    )
                )
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.error_loading_data), style = TextStyle(
                        fontSize = 20.sp, color = Color.Red
                    )
                )
            }
        }
    }
}

@Composable
fun ListCategoryFood(
    foods: MutableList<Food>,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(foods) { food ->
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

