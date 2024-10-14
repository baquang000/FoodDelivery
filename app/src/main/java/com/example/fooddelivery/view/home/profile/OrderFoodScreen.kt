package com.example.fooddelivery.view.home.profile

import android.widget.Toast
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.model.GetOrderItem
import com.example.fooddelivery.data.model.OrderStatus
import com.example.fooddelivery.data.model.tabItemOrder
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel.OrderFoodViewModel
import com.example.fooddelivery.navigation.ProfileRouteScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderFoodScreen(
    navController: NavController,
    orderViewModel: OrderFoodViewModel,
    sharedViewModel: SharedViewModel
) {
    val pagerState = rememberPagerState { tabItemOrder.size }
    val coroutineScope = rememberCoroutineScope()
    ///order
    val orderList by orderViewModel.orderFoodStateFlow.collectAsStateWithLifecycle()
    val isLoadingOrder by orderViewModel.isLoadingOrder.collectAsStateWithLifecycle()
    val isUpdate by sharedViewModel.isUpdateOrder.collectAsStateWithLifecycle()
    LaunchedEffect(isUpdate) {
        orderViewModel.getOrderByUser()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.order_screen),
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 130.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                },
            )
        }
    ) { padding ->
        Box {
            if (isLoadingOrder) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    TabRow(selectedTabIndex = pagerState.currentPage) {
                        tabItemOrder.forEachIndexed { index, tabItem ->
                            Tab(
                                selected = true,
                                onClick = {
                                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                                },
                                text = { Text(text = tabItem.title) },
                            )
                        }
                    }
                    HorizontalPager(
                        modifier = Modifier.weight(1f),
                        state = pagerState
                    ) { index ->
                        //Some screen
                        if (index == 0) {
                            ConfirmOrder(
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                coroutineScope = coroutineScope
                            )
                        }
                        if (index == 1) {
                            DeliveringOrder(
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                coroutineScope = coroutineScope
                            )
                        }
                        if (index == 2) {
                            SuccessOrder(
                                sharedViewModel = sharedViewModel,
                                navController = navController,
                                orderList = orderList,
                                orderViewModel = orderViewModel,
                            )
                        }
                        if (index == 3) {
                            CanceledOrder(
                                sharedViewModel = sharedViewModel,
                                orderList = orderList,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmOrder(
    orderList: List<GetOrderItem>,
    orderViewModel: OrderFoodViewModel,
    coroutineScope: CoroutineScope
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(orderList) { order ->
            if (order.orderStatus == OrderStatus.PENDING.toString()) {
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
                            Text(
                                text = order.user.address,
                                style = MaterialTheme.typography.titleMedium
                            )
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
                        items(order.orderDetails) { orderDetail ->
                            Column(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 2.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                AsyncImage(
                                    model = orderDetail.imagePath,
                                    contentDescription = orderDetail.title,
                                    modifier = Modifier.size(60.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Text(
                                    text = orderDetail.title,
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
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Text(
                            text = order.createdAt,
                            style = MaterialTheme.typography.titleMedium
                        )
                        NormalTextComponents(
                            value = "Tổng: ${order.totalMoney}đ",
                            nomalColor = colorResource(id = R.color.black),
                            nomalFontsize = 14.sp
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    orderViewModel.updateStatusWithApi(
                                        idOrder = order.id,
                                        orderStatus = OrderStatus.DELIVERY.toString()
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
        }
    }
}

@Composable
fun DeliveringOrder(
    orderViewModel: OrderFoodViewModel, orderList: List<GetOrderItem>,
    coroutineScope: CoroutineScope
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(orderList) { order ->
            if (order.orderStatus == OrderStatus.DELIVERY.toString()) {
                Column(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.white)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp, bottom = 2.dp),
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
                            Text(
                                text = order.user.address,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        NormalTextComponents(
                            value = stringResource(id = R.string.delevering_order),
                            nomalColor = colorResource(id = R.color.red),
                            nomalFontsize = 16.sp
                        )
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        items(order.orderDetails) { orderDetail ->
                            Column(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 2.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                AsyncImage(
                                    model = orderDetail.imagePath,
                                    contentDescription = orderDetail.title,
                                    modifier = Modifier.size(60.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Text(
                                    text = orderDetail.title,
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
                        Text(
                            text = order.updatedAt.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        NormalTextComponents(
                            value = "Tổng: ${order.totalMoney}đ",
                            nomalColor = colorResource(id = R.color.black),
                            nomalFontsize = 14.sp
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    orderViewModel.updateStatusWithApi(
                                        idOrder = order.id,
                                        orderStatus = OrderStatus.SUCCESS.toString()
                                    )
                                }
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.red),
                            ),
                            modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
                        ) {
                            NormalTextComponents(value = stringResource(R.string.delivered_order))
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun SuccessOrder(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    orderList: List<GetOrderItem>,
    orderViewModel: OrderFoodViewModel
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top
    ) {
        items(orderList) { order ->
            if (order.orderStatus == OrderStatus.SUCCESS.toString()) {
                Column(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.white)),
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
                            Text(
                                text = order.user.address,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        NormalTextComponents(
                            value = stringResource(id = R.string.delivered_order),
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
                        items(order.orderDetails) { orderDetail ->
                            Column(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 2.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                AsyncImage(
                                    model = orderDetail.imagePath,
                                    contentDescription = orderDetail.title,
                                    modifier = Modifier.size(60.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Text(
                                    text = orderDetail.title,
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
                        Text(
                            text = order.updatedAt.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        NormalTextComponents(
                            value = "Tổng: ${order.totalMoney}đ",
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
                                navController.navigate(route = ProfileRouteScreen.Comment.route) {
                                    launchSingleTop = true
                                }
                                orderViewModel.setIdOrder(idOrder = order.id)
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.red),
                            ),
                            modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
                        ) {
                            NormalTextComponents(value = stringResource(R.string.comment_order))
                        }
                        Button(
                            onClick = {
                                order.orderDetails.forEach { foodDetails ->
                                    val food = FoodDetails(
                                        idFood = foodDetails.idFood,
                                        imagePath = foodDetails.imagePath,
                                        price = foodDetails.price.toFloat(),
                                        quantity = foodDetails.quantity,
                                        title = foodDetails.title
                                    )

                                    sharedViewModel.addFoodDetail(foodDetails = food)
                                    Toast.makeText(
                                        context,
                                        "Đã thêm lại vào giỏ hàng",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.red),
                            ),
                            modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
                        ) {
                            NormalTextComponents(value = stringResource(R.string.re_order))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun CanceledOrder(
    sharedViewModel: SharedViewModel,
    orderList: List<GetOrderItem>,
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(orderList) { order ->
            if (order.orderStatus == OrderStatus.CANCEL.toString()) {
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
                            Text(
                                text = order.user.address,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        NormalTextComponents(
                            value = stringResource(id = R.string.cancel_order),
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
                        items(order.orderDetails) { orderDetail ->
                            Column(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 2.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                AsyncImage(
                                    model = orderDetail.imagePath,
                                    contentDescription = orderDetail.title,
                                    modifier = Modifier.size(60.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Text(
                                    text = orderDetail.title,
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
                        Text(
                            text = order.updatedAt.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        NormalTextComponents(
                            value = "Tổng: ${order.totalMoney}đ",
                            nomalColor = colorResource(id = R.color.black),
                            nomalFontsize = 14.sp
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                order.orderDetails.forEach { foodDetails ->
                                    val food = FoodDetails(
                                        idFood = foodDetails.idFood,
                                        imagePath = foodDetails.imagePath,
                                        price = foodDetails.price.toFloat(),
                                        quantity = foodDetails.quantity,
                                        title = foodDetails.title
                                    )

                                    sharedViewModel.addFoodDetail(foodDetails = food)
                                    Toast.makeText(
                                        context,
                                        "Đã thêm lại vào giỏ hàng",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.red),
                            ),
                            modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
                        ) {
                            NormalTextComponents(value = stringResource(R.string.re_order))
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}
