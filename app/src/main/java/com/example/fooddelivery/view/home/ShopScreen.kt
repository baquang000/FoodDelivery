package com.example.fooddelivery.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.CustomSnackBar
import com.example.fooddelivery.components.RatingBar
import com.example.fooddelivery.data.model.Calender
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.viewmodel.FavoriteViewModel
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.homeviewmodel.ShopViewModel
import com.example.fooddelivery.navigation.HomeRouteScreen
import com.example.fooddelivery.navigation.ID_SHOP_ARGUMENT_KEY
import kotlinx.coroutines.launch
import java.net.URLEncoder

@Composable
fun ShopScreen(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    innerPaddingValues: PaddingValues,
    sharedViewModel: SharedViewModel,
    shopViewModel: ShopViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val totalPrice = sharedViewModel.totalPrice.collectAsState()
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val shopProfileFlow by shopViewModel.shopProfileStateFlow.collectAsState()
    val foodStateFlow by shopViewModel.getFoodStateFlow.collectAsState()
    val idshop = navBackStackEntry.arguments?.getString(ID_SHOP_ARGUMENT_KEY)
    val countStateFlow by sharedViewModel.countFoodInCart.collectAsState()
    LaunchedEffect(countStateFlow) {
        if (countStateFlow > 0) {
            coroutineScope.launch {
                snackState.showSnackbar("", duration = SnackbarDuration.Indefinite)
            }
        } else {
            snackState.currentSnackbarData?.dismiss()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        shopProfileFlow.forEach { shop ->
            if (idshop == shop.idshop) {
                sharedViewModel.getNameShop(shop.name)
                LazyColumn(
                    modifier = Modifier
                        .background(color = Color.LightGray.copy(alpha = 0.5f))
                        .padding(innerPaddingValues)
                ) {
                    item {
                        HeadingWithBackgroudFood(
                            navController = navController,
                            imagepath = shop.imageUrl,
                            title = shop.titleShop,
                            modifier = Modifier.heightIn(max = 200.dp),
                            iconColor = Color.Black
                        )
                    }
                    item {
                        TitleShop(
                            titleShop = shop.titleShop.toString(), star = shop.starShop!!,
                            favoriteViewModel = favoriteViewModel,
                            id = shop.idshop.toString()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        DeliveryTime()
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(foodStateFlow) { food ->
                        if (food.idShop == idshop) {
                            FoodItemInShop(
                                food = food,
                                sharedViewModel = sharedViewModel,
                                navController = navController
                            )
                        }
                    }
                    if (countStateFlow > 0) {
                        item {
                            Spacer(modifier = Modifier.height(65.dp))
                        }
                    }
                }
            }
        }
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(color = Color.White),
            hostState = snackState
        ) {
            CustomSnackBar(
                countFood = countStateFlow,
                price = totalPrice.value.toDouble()
            ) {
                navController.navigate(route = HomeRouteScreen.CartHomeRouteScreen.route)
            }
        }
    }
}

@Composable
fun TitleShop(
    titleShop: String, star: Double,
    favoriteViewModel: FavoriteViewModel,
    id: String
) {
    LaunchedEffect(key1 = Unit) {
        favoriteViewModel.loadFavoriteShop(id)
    }
    val favoriteShopStateFlow by favoriteViewModel.favoriteShopStateFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
    ) {
        Text(
            text = titleShop, style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(
                rating = star.toFloat()
            ) {

            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = star.toString(), style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "(999+ Bình luận)> |", style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.time),
                contentDescription = stringResource(
                    id = R.string.time
                ),
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "30 phút", style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = if (favoriteShopStateFlow) painterResource(id = R.drawable.favourite) else painterResource(
                        id = R.drawable.favorite_white
                    ),
                    contentDescription = stringResource(
                        id = R.string.favorite_white_icon
                    ),
                    modifier = Modifier
                        .size(32.dp, 32.dp)
                        .clickable {
                            favoriteViewModel.saveFavoriteFood(
                                id = id,
                                isFavorite = !favoriteShopStateFlow
                            )

                        }
                )
            }
        }

    }
}

@Composable
fun FoodItemInShop(
    food: Food,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    val foodDetailStateFlow by sharedViewModel.foodDetailStateFlow.collectAsState()
    var numberOfFood by remember {
        mutableIntStateOf(
            foodDetailStateFlow.find { it.id == food.Id }?.quantity ?: 0
        )
    }
    val encodeURL = URLEncoder.encode(food.ImagePath, "UTF-8")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable {
                navController.navigate(
                    HomeRouteScreen.FoodDetails.sendFood(
                        title = food.Title.toString(),
                        price = food.Price,
                        star = food.Star,
                        timevalue = food.TimeValue,
                        description = food.Description.toString(),
                        imagepath = encodeURL,
                        id = food.Id,
                        idshop = food.idShop
                    )
                )
            }
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        AsyncImage(
            food.ImagePath,
            contentDescription = food.Title,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = food.Title.toString(), style = MaterialTheme.typography.titleMedium)
            Text(
                text = food.Description.toString(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "10k Đã bán", style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = "193 lượt thích", style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "${food.Price} đ", style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Red
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (numberOfFood > 0) {
                        IconButton(
                            onClick = {
                                numberOfFood--
                                val fooddetails = FoodDetails(
                                    title = food.Title.toString(),
                                    imagePath = food.ImagePath.toString(),
                                    price = food.Price.toFloat(),
                                    quantity = 1,
                                    id = food.Id
                                )
                                sharedViewModel.deleteFoodDetail(foodDetails = fooddetails)
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.minus_button),
                                contentDescription = stringResource(
                                    id = R.string.minus
                                ),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Text(
                            text = numberOfFood.toString(),
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    IconButton(
                        onClick = {
                            numberOfFood++
                            val fooddetails = FoodDetails(
                                title = food.Title.toString(),
                                imagePath = food.ImagePath.toString(),
                                price = food.Price.toFloat(),
                                quantity = 1,
                                id = food.Id
                            )
                            sharedViewModel.addFoodDetail(foodDetails = fooddetails)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = stringResource(
                                id = R.string.plus
                            ),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeliveryTime() {
    val time = Calender().getTimePlusOneHour()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.truck), contentDescription = "delivery",
            modifier = Modifier
                .size(30.dp)
                .background(shape = CircleShape, color = Color.LightGray)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Giao hàng tiêu chuẩn", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "Dự kiến giao hàng lúc $time",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

/*@Preview(
    showSystemUi = true
)
@Composable
fun ShopSreenPreview() {
    ShopScreen()
}*/
