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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.FoodItemInGird
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.category.ChickenViewModel

@Composable
fun ChickenScreen(
    navController: NavController,
    chickenViewModel: ChickenViewModel = viewModel(),
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues
) {
    val chicken by chickenViewModel.chickenFood.collectAsStateWithLifecycle()
    val isLoad by chickenViewModel.isLoadChicken.collectAsStateWithLifecycle()
    Box {
        if (isLoad) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
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
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(chicken) { chicken ->
                        FoodItemInGird(
                            food = chicken,
                            navController = navController,
                            buttonSize = 16.sp,
                            sharedViewModel = sharedViewModel
                        )
                    }
                }
            }
        }
    }
}
