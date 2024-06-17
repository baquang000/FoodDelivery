package com.example.fooddelivery.view.home

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.components.RatingBar
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.viewmodel.FavoriteViewModel
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY

@Composable
fun FoodDetailsScreen(
    navController: NavController, navBackStackEntry: NavBackStackEntry,
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues,
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val context = LocalContext.current
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
    var quantityFood by remember {
        mutableIntStateOf(0)
    }
    val totalPrice = quantityFood * price.toFloat()
    LaunchedEffect(key1 = id) {
        favoriteViewModel.loadFavoriteStatus(id)
    }
    var isFavorited by remember { mutableStateOf(false) }
    isFavorited = favoriteViewModel.favoriteStatus[id] ?: false
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
    ) {
        Box {
            AsyncImage(
                model = imagepath.toString(),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
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
                    tint = Color.White
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = if (isFavorited) painterResource(id = R.drawable.favourite) else painterResource(
                    id = R.drawable.favorite_white
                ),
                contentDescription = stringResource(
                    id = R.string.favorite_white_icon
                ),
                modifier = Modifier
                    .size(32.dp, 32.dp)
                    .scale(2.2f)
                    .clickable {
                        favoriteViewModel.saveFavoriteFood(
                            id = id,
                            isFavorited = !isFavorited
                        )
                    }
            )
        }
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
                value = "${price}đ",
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
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            NormalTextComponents(
                value = stringResource(R.string.quantity),
                nomalFontsize = 22.sp,
                nomalColor = Color.Black,
                nomalFontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = stringResource(
                        R.string.minus
                    ),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (quantityFood > 0) {
                                quantityFood--
                            }
                        }
                )
                NormalTextComponents(
                    value = quantityFood.toString(),
                    nomalColor = Color.Black,
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = stringResource(
                        R.string.plus
                    ),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            quantityFood++
                        },
                    colorFilter = ColorFilter.tint(Color.Red)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(color = Color.LightGray.copy(alpha = 0.3f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                NormalTextComponents(
                    value = stringResource(R.string.total),
                    nomalFontsize = 16.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "${totalPrice}đ",
                    nomalFontsize = 16.sp,
                    nomalColor = Color.Black
                )
            }
            Button(
                onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = stringResource(
                        R.string.cart
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                NormalTextComponents(value = stringResource(R.string.add_to_cart),
                    modifier = Modifier.clickable {
                        val fooddetails = FoodDetails(
                            title = title,
                            imagePath = imagepath,
                            price = price.toFloat(),
                            quantity = quantityFood
                        )
                        sharedViewModel.addFoodDetail(foodDetails = fooddetails)
                        Toast.makeText(context, "Thêm vào giỏ hàng thành công", LENGTH_SHORT).show()
                    })
            }
        }
    }
}
