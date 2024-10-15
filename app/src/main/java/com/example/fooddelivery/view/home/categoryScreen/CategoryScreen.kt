package com.example.fooddelivery.view.home.categoryScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.FoodItemInGird
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.category.CategoryViewModel
import com.example.fooddelivery.navigation.CATEGORY_ID_KEY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel = viewModel(),
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues,
    backStackEntry: NavBackStackEntry,
) {
    val id = backStackEntry.arguments?.getInt(CATEGORY_ID_KEY) ?: 0
    LaunchedEffect(key1 = Unit) {
        categoryViewModel.initCategory(id)
    }
    val chicken by categoryViewModel.categoryFood.collectAsStateWithLifecycle()
    val isLoad by categoryViewModel.isLoadCategory.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Đồ ăn",
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 130.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.gray_background)
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
                },
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            if (isLoad) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddingValues)
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

