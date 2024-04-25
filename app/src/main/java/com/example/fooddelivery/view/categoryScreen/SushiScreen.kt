package com.example.fooddelivery.view.categoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.fooddelivery.data.viewmodel.categoryviewmodel.ShushiViewModel

@Composable
fun ShushiScreen(navController: NavController, shushiViewModel: ShushiViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize()
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
            SetMoreItem(shushiViewModel = shushiViewModel)
        }
    }
}

@Composable
fun SetMoreItem(shushiViewModel: ShushiViewModel) {
    when (val result = shushiViewModel.shushiFood.value) {
        is FoodState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is FoodState.Success -> {
            ListCategoryFood(result.data)
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
