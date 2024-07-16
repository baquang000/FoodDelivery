package com.example.fooddelivery.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.viewmodel.FavoriteViewModel
import com.example.fooddelivery.navigation.FavoriteRouteScreen
import java.net.URLEncoder
import java.text.DecimalFormat

@Composable
fun FavoriteScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val favoriteFood by favoriteViewModel::favoriteFood
    val isLoading by favoriteViewModel::isLoading
    val loadError by favoriteViewModel::loadError
    LaunchedEffect(Unit) {
        favoriteViewModel.getFavoriteFood()
    }
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(favoriteFood) { food ->
                    FavoriteItem(food, navController = navController)
                }
            }
            if (loadError != null) {
                Text(text = loadError ?: "", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun FavoriteItem(
    food: Food,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val encodeURL = URLEncoder.encode(food.ImagePath, "UTF-8")
    val decimalFormat = DecimalFormat("#,###.##")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                navController.navigate(
                    FavoriteRouteScreen.DetailFavorite.sendFood(
                        title = food.Title.toString(),
                        price = food.Price,
                        star = food.Star,
                        timevalue = food.TimeValue,
                        description = food.Description.toString(),
                        imagepath = encodeURL,
                        id = food.Id
                    )
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = food.ImagePath,
            contentDescription = food.Title,
            contentScale = ContentScale.Inside,
            modifier = Modifier.size(100.dp, 100.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = food.Title.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxHeight()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
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
                        modifier = Modifier.size(24.dp, 24.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
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
                            .size(20.dp, 20.dp),
                        tint = Color.Red
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            NormalTextComponents(
                value = "${decimalFormat.format(food.Price)}đ",
                nomalColor = Color.Black,
                nomalFontWeight = FontWeight.Bold,
                nomalFontsize = 18.sp,
            )
        }
    }
}
