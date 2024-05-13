package com.example.fooddelivery.view.home

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.FoodItem
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.example.fooddelivery.data.viewmodel.SearchViewModel
import com.example.fooddelivery.data.viewmodel.SharedViewModel
import com.example.fooddelivery.navigation.SEARCH_ARGUMENT_KEY

@Composable
fun SearchScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    searchViewModel: SearchViewModel = viewModel(),
    sharedViewModel: SharedViewModel = viewModel(),
    innerPaddingValues: PaddingValues
) {
    val text = backStackEntry.arguments?.getString(SEARCH_ARGUMENT_KEY)
    Column {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = stringResource(R.string.arrow)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPaddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SetFoodItemsWithSearch(
                searchViewModel = searchViewModel,
                text = text,
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

@Composable
fun SetFoodItemsWithSearch(
    searchViewModel: SearchViewModel,
    text: String?,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    when (val result = searchViewModel.searchFood.value) {
        is FoodState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FoodState.Success -> {
            ListItemFoodWithSearch(
                result.data,
                text,
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }

        is FoodState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    fontSize = 20.sp
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.error_loading_data),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun ListItemFoodWithSearch(
    foods: MutableList<Food>,
    text: String?,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(foods.filter {
            (it.Title?.lowercase() ?: "").contains(text?.lowercase() ?: "")
        }) { food ->
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
