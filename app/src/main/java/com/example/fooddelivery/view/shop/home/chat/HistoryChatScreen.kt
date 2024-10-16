package com.example.fooddelivery.view.shop.home.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.GetUser
import com.example.fooddelivery.data.viewmodel.shop.ChatByShopViewModel
import com.example.fooddelivery.navigation.ShopRouteScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryChatScreen(
    navController: NavController,
    chatViewModel: ChatByShopViewModel = viewModel()
) {
    val isLoading by chatViewModel.loadingUser.collectAsStateWithLifecycle()
    val user by chatViewModel.userHistory.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Tin nhắn",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 45.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(user) { user ->
                        ItemHistory(user = user, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemHistory(user: GetUser, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .heightIn(min = 60.dp)
            .background(color = Color.LightGray.copy(alpha = 0.3f))
            .clickable {
                navController.navigate(
                    route = ShopRouteScreen.ChatByShop.sendId(
                        idUser = user.id,
                        title = user.name
                    )
                )
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.person),
            tint = Color.Black,
            contentDescription = null,
            modifier = Modifier.size(36.dp)
        )
        NormalTextComponents(
            value = user.name, nomalColor = Color.Black, nomalFontsize = 20.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
