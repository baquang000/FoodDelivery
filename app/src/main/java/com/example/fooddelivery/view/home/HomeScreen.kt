package com.example.fooddelivery.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.FoodItem
import com.example.fooddelivery.components.IconButtonWithText
import com.example.fooddelivery.components.MyDropdownMenuWithLoc
import com.example.fooddelivery.components.MyDropdownMenuWithPrice
import com.example.fooddelivery.components.MyDropdownMenuWithTime
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.example.fooddelivery.data.model.LocationState
import com.example.fooddelivery.data.model.PriceState
import com.example.fooddelivery.data.model.TimeState
import com.example.fooddelivery.data.viewmodel.HomeViewModel
import com.example.fooddelivery.data.viewmodel.SharedViewModel
import com.example.fooddelivery.navigation.Screen
import com.example.fooddelivery.ui.theme.category_btn_1
import com.example.fooddelivery.ui.theme.category_btn_2
import com.example.fooddelivery.ui.theme.category_btn_3
import com.example.fooddelivery.ui.theme.category_btn_4
import com.example.fooddelivery.ui.theme.category_btn_5
import com.example.fooddelivery.ui.theme.category_btn_6
import com.example.fooddelivery.ui.theme.category_btn_7

@Composable
fun HomeScreen(
    navController: NavController, homeViewModel: HomeViewModel = viewModel(),
    sharedViewModel : SharedViewModel
) {
    var searchText by remember {
        mutableStateOf("")
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.heightIn(min = 48.dp)
                ) {
                    NormalTextComponents(
                        value = stringResource(R.string.welcome),
                        nomalColor = Color.Black,
                    )
                    NormalTextComponents(
                        value = "Quảng", nomalColor = Color.Black, nomalFontWeight = FontWeight.Bold
                    )
                }
                IconButton(modifier = Modifier.size(32.dp, 32.dp), onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = stringResource(
                            R.string.logout_icon
                        ),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                        .weight(1f),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = stringResource(
                            R.string.setting_logo
                        ),
                        tint = Color.Red,
                        modifier = Modifier.scale(1.7f)
                    )
                }
                OutlinedTextField(value = searchText, onValueChange = {
                    searchText = it
                }, leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = stringResource(
                            R.string.search_icon
                        ),
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Search.sendText(searchText))
                        }
                    )
                }, placeholder = {
                    Text(text = stringResource(R.string.search_food))
                }, modifier = Modifier.weight(6f), colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.White,
                    focusedLeadingIconColor = Color.Blue,
                    unfocusedLeadingIconColor = Color.Red
                ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(
                                R.string.close
                            ),
                            modifier = Modifier.clickable {
                                searchText = ""
                            }
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            navController.navigate(Screen.Search.sendText(searchText))
                        }
                    )
                )
                IconButton(
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                        .weight(1f),
                    onClick = { navController.navigate(route = Screen.CartScreen.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.basket),
                        contentDescription = stringResource(
                            R.string.basket_logo
                        ),
                        tint = Color.Red,
                        modifier = Modifier.scale(1.7f)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            ) {
                LazyRow()
                {
                    item {
                        SetLocationItems(
                            homeViewModel = homeViewModel,
                            modifier = Modifier
                                .widthIn(max = 200.dp)
                                .padding(horizontal = 8.dp)
                        )
                        SetTimeIems(
                            homeViewModel = homeViewModel,
                            modifier = Modifier
                                .widthIn(max = 200.dp)
                                .padding(horizontal = 8.dp)
                        )
                        SetPriceIems(
                            homeViewModel = homeViewModel,
                            modifier = Modifier
                                .widthIn(max = 200.dp)
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 8.dp)
                    .heightIn(32.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NormalTextComponents(
                        value = stringResource(R.string.today_best_food),
                        nomalFontWeight = FontWeight.Bold,
                        nomalColor = Color.Black, nomalFontsize = 22.sp,
                    )
                    NormalTextComponents(
                        value = stringResource(R.string.view_all), nomalColor = Color.Red,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.ViewAll.route)
                        }
                    )
                }
                SetFoodItems(homeViewModel = homeViewModel, navController = navController, sharedViewModel = sharedViewModel)
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    NormalTextComponents(
                        value = stringResource(R.string.choose_category),
                        nomalColor = Color.Black,
                        nomalFontsize = 22.sp,
                        nomalFontWeight = FontWeight.Bold
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButtonWithText {
                                navController.navigate(Screen.Pizza.route)
                            }
                            IconButtonWithText(
                                backgroundColor = category_btn_1,
                                iconId = R.drawable.btn_2,
                                textId = R.string.Burger
                            ) {
                                navController.navigate(Screen.Burger.route)
                            }
                            IconButtonWithText(
                                backgroundColor = category_btn_2,
                                iconId = R.drawable.btn_3,
                                textId = R.string.Chicken
                            ) {
                                navController.navigate(Screen.Chicken.route)
                            }
                            IconButtonWithText(
                                backgroundColor = category_btn_3,
                                iconId = R.drawable.btn_4,
                                textId = R.string.Sushi
                            ) {
                                navController.navigate(Screen.Shushi.route)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButtonWithText(
                                backgroundColor = category_btn_4,
                                iconId = R.drawable.btn_5,
                                textId = R.string.Meat
                            ) {
                                navController.navigate(Screen.Meat.route)
                            }
                            IconButtonWithText(
                                backgroundColor = category_btn_5,
                                iconId = R.drawable.btn_6,
                                textId = R.string.hot_dog
                            ) {
                                navController.navigate(Screen.HotDog.route)
                            }
                            IconButtonWithText(
                                backgroundColor = category_btn_6,
                                iconId = R.drawable.btn_7,
                                textId = R.string.drink
                            ) {
                                navController.navigate(Screen.Drink.route)
                            }
                            IconButtonWithText(
                                backgroundColor = category_btn_7,
                                iconId = R.drawable.btn_8,
                                textId = R.string.more
                            ) {
                                navController.navigate(Screen.More.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetPriceIems(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    when (val result = homeViewModel.price.value) {
        is PriceState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is PriceState.Success -> {
            MyDropdownMenuWithPrice(price = result.data, modifier = modifier)

        }

        is PriceState.Failure -> {
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
fun SetTimeIems(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    when (val result = homeViewModel.time.value) {
        is TimeState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is TimeState.Success -> {
            MyDropdownMenuWithTime(time = result.data, modifier = modifier)

        }

        is TimeState.Failure -> {
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
fun SetFoodItems(homeViewModel: HomeViewModel, navController: NavController,sharedViewModel: SharedViewModel) {
    when (val result = homeViewModel.responseBestFood.value) {
        is FoodState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is FoodState.Success -> {
            ListItemFood(result.data, navController = navController, sharedViewModel = sharedViewModel)

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
fun ListItemFood(
    foods: MutableList<Food>,
    navController: NavController,
    sharedViewModel: SharedViewModel,
) {
    LazyRow(
        modifier = Modifier.background(color = Color.White)
    ) {
        items(foods) { food ->
            FoodItem(food = food, navController = navController, sharedViewModel = sharedViewModel)
        }
    }
}

@Composable
fun SetLocationItems(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    when (val result = homeViewModel.location.value) {
        is LocationState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LocationState.Success -> {
            MyDropdownMenuWithLoc(location = result.data, modifier = modifier)
        }

        is LocationState.Failure -> {
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

