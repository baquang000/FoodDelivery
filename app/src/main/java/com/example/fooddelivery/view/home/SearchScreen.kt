package com.example.fooddelivery.view.home

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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.FoodItemInGird
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.ViewAllViewModel
import com.example.fooddelivery.navigation.SEARCH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TYPE_ARGUMENT_KEY

@Composable
fun SearchScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    viewAllViewModel: ViewAllViewModel = viewModel(),
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues
) {
    val text = backStackEntry.arguments?.getString(SEARCH_ARGUMENT_KEY)
    val type = backStackEntry.arguments?.getString(TYPE_ARGUMENT_KEY)
    val allFood by viewAllViewModel.allFood.collectAsStateWithLifecycle()
    val isLoad by viewAllViewModel.isLoadAllFood.collectAsStateWithLifecycle()
    Box {
        if (isLoad) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier.padding(innerPaddingValues)
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
                    when (type) {
                        "titleFood" -> {
                            items(allFood.filter {
                                text?.let { searchText ->
                                    it.title.lowercase().contains(searchText.lowercase())
                                } ?: true
                            }) { food ->
                                FoodItemInGird(
                                    food = food,
                                    navController = navController,
                                    buttonSize = 16.sp,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }

                        "price" -> {
                            items(allFood.filter {
                                text?.let { searchText ->
                                    it.price.price.contains(searchText)
                                } ?: true
                            }) { food ->
                                FoodItemInGird(
                                    food = food,
                                    navController = navController,
                                    buttonSize = 16.sp,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }

                        "time" -> {
                            items(allFood.filter {
                                text?.let { searchText ->
                                    it.time.time.contains(searchText)
                                } ?: true
                            }) { food ->
                                FoodItemInGird(
                                    food = food,
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

    }
}



