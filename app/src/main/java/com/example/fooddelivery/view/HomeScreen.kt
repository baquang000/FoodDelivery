package com.example.fooddelivery.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.IconButtonWithText
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.example.fooddelivery.data.viewmodel.HomeViewModel
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
    navController: NavController, homeViewModel: HomeViewModel = viewModel()
) {
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
                OutlinedTextField(value = "", onValueChange = {}, trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = stringResource(
                            R.string.search_icon
                        ),
                        tint = Color.Red
                    )
                }, placeholder = {
                    Text(text = stringResource(R.string.search_food))
                }, modifier = Modifier.weight(6f), colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    focusedTrailingIconColor = Color.Blue
                )
                )
                IconButton(
                    modifier = Modifier
                        .size(48.dp, 48.dp)
                        .weight(1f),
                    onClick = { /*TODO*/ }) {
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
                    .padding(top = 16.dp)
                    .heightIn(32.dp),
            ) {
                Row(
                    modifier = Modifier
                        .heightIn(32.dp)
                        .background(color = Color.LightGray.copy(0.3f))
                        .widthIn(min = 48.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = stringResource(
                            R.string.location_icon
                        ),
                        tint = Color.Black,
                        modifier = Modifier.scale(1.8f)
                    )
                    Text(
                        text = stringResource(R.string.location),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = stringResource(
                            R.string.location_icon
                        ),
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp, 14.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(
                    modifier = Modifier
                        .heightIn(32.dp)
                        .background(color = Color.LightGray.copy(0.3f))
                        .widthIn(min = 48.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = stringResource(
                            R.string.location_icon
                        ),
                        tint = Color.Black,
                        modifier = Modifier.scale(1.8f)
                    )
                    Text(
                        text = stringResource(R.string.time),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = stringResource(
                            R.string.location_icon
                        ),
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp, 14.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(
                    modifier = Modifier
                        .heightIn(32.dp)
                        .background(color = Color.LightGray.copy(0.3f))
                        .widthIn(min = 48.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dollar),
                        contentDescription = stringResource(
                            R.string.location_icon
                        ),
                        tint = Color.Black,
                        modifier = Modifier.scale(1.8f)
                    )
                    Text(
                        text = stringResource(R.string.price),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = stringResource(
                            R.string.location_icon
                        ),
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp, 14.dp)
                    )
                }
            }
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
                    value = stringResource(R.string.view_all), nomalColor = Color.Red
                )
            }
            SetFoodItems(homeViewModel)
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
                        IconButtonWithText{
                            navController.navigate(Screen.Pizza.route)
                        }
                        IconButtonWithText(
                            backgroundColor = category_btn_1,
                            iconId = R.drawable.btn_2,
                            textId = R.string.Burger
                        ){
                            navController.navigate(Screen.Burger.route)
                        }
                        IconButtonWithText(
                            backgroundColor = category_btn_2,
                            iconId = R.drawable.btn_3,
                            textId = R.string.Chicken
                        ){
                            navController.navigate(Screen.Chicken.route)
                        }
                        IconButtonWithText(
                            backgroundColor = category_btn_3,
                            iconId = R.drawable.btn_4,
                            textId = R.string.Sushi
                        ){
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
                        ){
                            navController.navigate(Screen.Meat.route)
                        }
                        IconButtonWithText(
                            backgroundColor = category_btn_5,
                            iconId = R.drawable.btn_6,
                            textId = R.string.hot_dog
                        ){
                            navController.navigate(Screen.HotDog.route)
                        }
                        IconButtonWithText(
                            backgroundColor = category_btn_6,
                            iconId = R.drawable.btn_7,
                            textId = R.string.drink
                        ){
                            navController.navigate(Screen.Drink.route)
                        }
                        IconButtonWithText(
                            backgroundColor = category_btn_7,
                            iconId = R.drawable.btn_8,
                            textId = R.string.more
                        ){
                            navController.navigate(Screen.More.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetFoodItems(homeViewModel: HomeViewModel) {
    when (val result = homeViewModel.responseBestFood.value) {
        is FoodState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is FoodState.Success -> {
            ListItemFood(result.data)

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
fun ListItemFood(foods: MutableList<Food>) {
    LazyRow(
        modifier = Modifier.background(color = Color.White)
    ) {
        items(foods) { food ->
            FoodItem(food = food)
        }
    }
}

@Composable
fun FoodItem(modifier: Modifier = Modifier, food: Food) {
    Card(
        modifier = modifier
            .size(width = 260.dp, height = 295.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .clip(shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model = food.ImagePath,
                contentDescription = food.Title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 190.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    NormalTextComponents(
                        value = food.Title.toString(),
                        nomalColor = Color.Black,
                        nomalFontsize = 18.sp,
                        nomalFontWeight = FontWeight.Bold,
                        nomalTextAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    NormalTextComponents(
                        value = "${food.Price}đ",
                        nomalColor = Color.Black,
                        nomalFontWeight = FontWeight.Bold,
                        nomalFontsize = 18.sp,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                        ) {
                            NormalTextComponents(
                                value = food.Star.toString(),
                                nomalColor = Color.Black,
                                nomalFontsize = 18.sp,
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = stringResource(
                                    R.string.star_icon
                                ),
                                tint = Color.Yellow,
                                modifier = Modifier.scale(1.8f)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            NormalTextComponents(
                                value = "${food.TimeValue}p",
                                nomalFontsize = 18.sp,
                                nomalColor = Color.Black,
                                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.time),
                                contentDescription = stringResource(
                                    id = R.string.time
                                ),
                                modifier = Modifier
                                    .scale(1.8f)
                                    .padding(start = 8.dp, bottom = 8.dp),
                                tint = Color.Red
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ), shape = RectangleShape, modifier = Modifier.clip(
                                shape = RoundedCornerShape(
                                    topStart = 15.dp, bottomEnd = 16.dp
                                )
                            ), onClick = { /*TODO*/ }) {
                                Text(
                                    text = "+", style = TextStyle(
                                        fontSize = 30.sp, textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}