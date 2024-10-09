package com.example.fooddelivery.view.shop.home.order

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
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
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun PendingOrderScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val order by homeViewModel.orderStateFlow.collectAsStateWithLifecycle()
    val isLoading by homeViewModel.isLoadingOrder.collectAsStateWithLifecycle()

    Box {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.gray_background)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    HeadingPending(navController = navController)
                }
                items(order) { order ->
                    if (order.orderStatus == OrderStatus.PENDING.toString()) {
                        PendingOrderFood(
                            order = order,
                            homeViewModel = homeViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeadingPending(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = stringResource(
                    id = R.string.arrow_icon
                ),
                modifier = Modifier.size(width = 24.dp, height = 24.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            NormalTextComponents(
                value = stringResource(id = R.string.order_comfirm),
                nomalColor = colorResource(id = R.color.black),
                nomalFontsize = 28.sp,
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }

}

@Composable
fun PendingOrderFood(order: GetOrderItem, homeViewModel: HomeViewModel) {
    val decimalFormat = DecimalFormat("#,###.##")
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.white)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, bottom = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                value = stringResource(id = R.string.order_comfirm),
                nomalColor = colorResource(id = R.color.red),
                nomalFontsize = 16.sp,
                modifier = Modifier.weight(3f)
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
            Text(text = order.createdAt, style = MaterialTheme.typography.titleMedium)
            NormalTextComponents(
                value = "Tổng: ${decimalFormat.format(order.totalMoney)}đ",
                nomalColor = colorResource(id = R.color.black),
                nomalFontsize = 14.sp
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        homeViewModel.updateStatusWithApi(
                            order.id,
                            OrderStatus.DELIVERY.toString()
                        )
                    }
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.red),
                ),
                modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
            ) {
                NormalTextComponents(value = stringResource(R.string.confirm))
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        homeViewModel.updateStatusWithApi(

                            order.id,
                            OrderStatus.CANCEL.toString()
                        )
                    }
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.red),
                ),
                modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
            ) {
                NormalTextComponents(value = stringResource(R.string.cancel_order))
            }
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}