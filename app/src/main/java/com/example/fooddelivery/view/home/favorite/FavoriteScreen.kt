package com.example.fooddelivery.view.home.favorite

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.Shop
import com.example.fooddelivery.data.viewmodel.FavoriteViewModel
import com.example.fooddelivery.navigation.HomeRouteScreen

@Composable
fun FavoriteScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val favoriteFood by favoriteViewModel.shopFovoriteStateFlow.collectAsState()
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
                items(favoriteFood) { shop ->
                    FavoriteItem(
                        shop,
                        navController = navController,
                        favoriteViewModel = favoriteViewModel
                    )
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
    shop: Shop,
    modifier: Modifier = Modifier,
    favoriteViewModel: FavoriteViewModel,
    navController: NavController
) {
    val favoriteShopStateFlow by favoriteViewModel.favoriteShopStateFlow.collectAsState()
    LaunchedEffect(Unit) {
        favoriteViewModel.loadFavoriteShop(shop.idshop!!)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                navController.navigate(route = HomeRouteScreen.ShopRouteScreen.sendIdShop(idshop = shop.idshop!!))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = shop.imageUrl,
            contentDescription = shop.titleShop,
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
            Text(
                text = shop.titleShop.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                ) {
                    NormalTextComponents(
                        value = shop.starShop.toString(),
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
                                id = shop.idshop!!,
                                isFavorite = !favoriteShopStateFlow
                            )

                        }
                )

            }
        }
    }
}
