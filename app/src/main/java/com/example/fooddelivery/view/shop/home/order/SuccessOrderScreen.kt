package com.example.fooddelivery.view.shop.home.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.shop.NormalTextComponents
import com.example.fooddelivery.data.model.GetOrderItem
import com.example.fooddelivery.data.model.OrderStatus
import com.example.fooddelivery.data.viewmodel.shop.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveredOrderScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val order by homeViewModel.orderStateFlow.collectAsStateWithLifecycle()
    val isLoading by homeViewModel.isLoadingOrder.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.delivered_order),
                        color = Color.Black,
                        modifier = Modifier.padding(start = 45.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.gray_background)
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = colorResource(id = R.color.gray_background)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(order) { order ->
                        if (order.orderStatus == OrderStatus.SUCCESS.toString() || order.orderStatus == OrderStatus.FOODBACK.toString()) {
                            DeliveredOrderFood(
                                order = order
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DeliveredOrderFood(order: GetOrderItem) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.white)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp, bottom = 2.dp, start = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(7f)
            ) {
                Text(
                    text = "${order.user.name} | ${order.user.numberPhone}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = order.user.address, style = MaterialTheme.typography.titleMedium)
            }
            NormalTextComponents(
                value = stringResource(id = R.string.complete_order),
                nomalColor = colorResource(id = R.color.red),
                nomalFontsize = 16.sp
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            items(order.orderDetails) { food ->
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 2.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    AsyncImage(
                        model = food.imagePath, contentDescription = food.title,
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.FillHeight
                    )
                    Text(
                        text = food.title,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.black),
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp, bottom = 2.dp, start = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = order.updatedAt.toString(), style = MaterialTheme.typography.titleMedium)
            NormalTextComponents(
                value = "Tổng: ${order.totalMoney}đ",
                nomalColor = colorResource(id = R.color.black),
                nomalFontsize = 14.sp
            )
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}