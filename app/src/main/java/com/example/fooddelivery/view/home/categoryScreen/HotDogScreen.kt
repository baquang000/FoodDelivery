package com.example.fooddelivery.view.home.categoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.FoodState
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.homeviewmodel.categoryviewmodel.HotDogViewModel

@Composable
fun HotDogScreen(
    navController: NavController,
    hotDogViewModel: HotDogViewModel = viewModel(),
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(innerPaddingValues)
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = stringResource(R.string.arrow)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SetMoreItem(
                hotDogViewModel = hotDogViewModel,
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

@Composable
fun SetMoreItem(
    hotDogViewModel: HotDogViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    when (val result = hotDogViewModel.hotdogFood.value) {
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
