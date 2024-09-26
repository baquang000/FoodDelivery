package com.example.fooddelivery.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.CustomSnackBar
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.components.RatingBar
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.HomeRouteScreen
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun FoodDetailsScreen(
    navController: NavController, navBackStackEntry: NavBackStackEntry,
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues,
) {
    ///snack bar
    val totalPriceSnackbar = sharedViewModel.totalPrice.collectAsState()
    val countStateFlow by sharedViewModel.countFoodInCart.collectAsState()
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(countStateFlow) {
        if (countStateFlow > 0) {
            coroutineScope.launch {
                snackState.showSnackbar("", duration = SnackbarDuration.Indefinite)
            }
        } else {
            snackState.currentSnackbarData?.dismiss()
        }
    }
    val countFood by sharedViewModel.countFoodInCart.collectAsState()
    var numberOfFood by remember {
        mutableIntStateOf(countFood)
    }
    /////
    val decimalFomat = DecimalFormat("#,###.##")
    val title = navBackStackEntry.arguments?.getString(TITLE_ARGUMENT_KEY)
    val price = navBackStackEntry.arguments?.getFloat(PRICE_ARGUMENT_KEY) ?: 0.0
    val star = navBackStackEntry.arguments?.getFloat(STAR_ARGUMENT_KEY) ?: 0.0
    val timevalue = navBackStackEntry.arguments?.getInt(TIMEVALUE_ARGUMENT_KEY)
    val description = navBackStackEntry.arguments?.getString(DESCRIPTION_ARGUMENT_KEY)
    val imagepath = navBackStackEntry.arguments?.getString(IMAGEPATH_ARGUMENT_KEY)
    val id = navBackStackEntry.arguments?.getInt(ID_ARGUMENT_KEY) ?: -1

    val rating by remember {
        mutableFloatStateOf(star.toFloat())
    }
    //comment value
    val commentValue by sharedViewModel.commentStateFlow.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddingValues)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HeadingWithBackgroudFood(
                        navController = navController,
                        imagepath = imagepath,
                        title = title
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        NormalTextComponents(
                            value = title.toString(),
                            nomalColor = Color.Black,
                            nomalFontsize = 18.sp,
                            nomalFontWeight = FontWeight.Bold,
                            nomalTextAlign = TextAlign.Center
                        )
                        NormalTextComponents(
                            value = "${decimalFomat.format(price)}đ",
                            nomalColor = Color.Red,
                            nomalFontWeight = FontWeight.Bold,
                            nomalFontsize = 18.sp,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            NormalTextComponents(
                                value = "${rating}/5",
                                nomalColor = Color.Black,
                                nomalFontsize = 16.sp
                            )
                            RatingBar(
                                modifier = Modifier.size(36.dp),
                                rating = rating
                            ) {}
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            NormalTextComponents(
                                value = "${timevalue.toString()}p",
                                nomalColor = Color.Black,
                                nomalFontsize = 16.sp,
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.time),
                                contentDescription = stringResource(
                                    id = R.string.time
                                ),
                                modifier = Modifier.size(16.dp),
                                tint = Color.Red
                            )
                        }
                    }
                    NormalTextComponents(
                        value = stringResource(R.string.details),
                        nomalFontsize = 22.sp,
                        nomalColor = Color.Black,
                        nomalFontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    )
                    NormalTextComponents(
                        value = description.toString(),
                        nomalFontsize = 14.sp,
                        nomalColor = Color.Black,
                        modifier = Modifier.padding(start = 16.dp),
                        nomalTextAlign = TextAlign.Start
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        NormalTextComponents(
                            value = stringResource(R.string.quantity),
                            nomalFontsize = 22.sp,
                            nomalColor = Color.Black,
                            nomalFontWeight = FontWeight.Bold,
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
                                            title = title.toString(),
                                            imagePath = imagepath.toString(),
                                            price = price.toFloat(),
                                            quantity = 1,
                                            idFood = id
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
                                        title = title.toString(),
                                        imagePath = imagepath.toString(),
                                        price = price.toFloat(),
                                        quantity = 1,
                                        idFood = id
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
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                ) {
                    Text(
                        text = "Bình luận", style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    )
                }
            }
            items(commentValue) { comment ->
                if (comment.idFood == id) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = comment.nameUser, style = MaterialTheme.typography.titleMedium,
                        )
                        RatingBar(
                            modifier = Modifier.size(26.dp),
                            rating = comment.rating
                        ) {}
                        Text(text = comment.comment, style = MaterialTheme.typography.titleMedium)
                        AsyncImage(
                            model = comment.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.size(width = 100.dp, height = 100.dp),
                            contentScale = ContentScale.FillWidth
                        )
                        Text(
                            text = comment.time, style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.Gray
                            )
                        )
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
                price = totalPriceSnackbar.value.toDouble()
            ) {
                navController.navigate(route = HomeRouteScreen.CartHomeRouteScreen.route){
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
fun HeadingWithBackgroudFood(
    navController: NavController,
    modifier: Modifier = Modifier,
    imagepath: String?, title: String?,
    iconColor: Color = Color.White,
) {
    Box {
        AsyncImage(
            model = imagepath.toString(),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
        )
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = stringResource(
                    id = R.string.arrow
                ),
                modifier = Modifier
                    .scale(2.5f)
                    .align(alignment = Alignment.TopStart)
                    .padding(16.dp),
                tint = iconColor
            )
        }
    }
}
