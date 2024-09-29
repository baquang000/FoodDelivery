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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.GetFavorite
import com.example.fooddelivery.data.viewmodel.FavoriteViewModel
import com.example.fooddelivery.navigation.HomeRouteScreen
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    val isLoading by favoriteViewModel.isLoadFavorite.collectAsStateWithLifecycle()
    val favoriteShopStateFlow by favoriteViewModel.favorite.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = favoriteShopStateFlow) {
        favoriteViewModel.getFavoriteWithApi()
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
                items(favoriteShopStateFlow) { shop ->
                    FavoriteItem(
                        shop,
                        navController = navController,
                        favoriteViewModel = favoriteViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    shop: GetFavorite,
    modifier: Modifier = Modifier,
    favoriteViewModel: FavoriteViewModel,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                navController.navigate(
                    route = HomeRouteScreen.ShopRouteScreen.sendIdShop(idshop = shop.idShop),
                    navOptions = NavOptions
                        .Builder()
                        .setLaunchSingleTop(true)
                        .build()
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = shop.shop.imageUrl,
            contentDescription = shop.shop.titleShop,
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
                text = shop.shop.titleShop,
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
                        value = shop.shop.starShop,
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
                    painter = painterResource(id = R.drawable.favourite),
                    contentDescription = stringResource(
                        id = R.string.favorite_white_icon
                    ),
                    modifier = Modifier
                        .size(32.dp, 32.dp)
                        .clickable {
                            coroutineScope.launch {
                                favoriteViewModel.deleteFavorite(idShop = shop.idShop)
                            }
                        }
                )
            }
        }
    }
}
