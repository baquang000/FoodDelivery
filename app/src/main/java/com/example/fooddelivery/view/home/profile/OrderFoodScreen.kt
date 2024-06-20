package com.example.fooddelivery.view.home.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.tabItemOrder
import com.example.fooddelivery.data.viewmodel.profileviewmodel.OrderFoodViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderFoodScreen(
    navController: NavController,
    orderViewModel: OrderFoodViewModel = viewModel()
) {
    val pagerState = rememberPagerState { tabItemOrder.size }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier.padding(end = 80.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp)
                )
            }
            NormalTextComponents(
                value = stringResource(id = R.string.order_screen),
                nomalFontsize = 24.sp,
                nomalColor = colorResource(id = R.color.black),
                nomalFontWeight = FontWeight.Bold
            )
        }
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
                ConfirmOrder(orderViewModel = orderViewModel)
            }
            if (index == 1) {
                DeliveringOrder(orderViewModel = orderViewModel)
            }
            if (index == 2) {
                DeliveredOrder(orderViewModel = orderViewModel)
            }
            if (index == 3) {
                CanceledOrder(orderViewModel = orderViewModel)
            }
        }
    }
}

@Composable
fun ConfirmOrder(orderViewModel: OrderFoodViewModel) {
    val orderList by orderViewModel.orderFoodStateFlow.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(orderList) { order ->
            if (!order.comfirm && !order.cancelled) {
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
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = stringResource(id = R.string.order_comfirm),
                            nomalColor = colorResource(id = R.color.red),
                            nomalFontsize = 16.sp
                        )
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        items(order.listFood) { food ->
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
                                    text = food.title.toString(),
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
                            .padding(end = 4.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = "Tổng: ${order.sumPrice}đ",
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
                                orderViewModel.canceledOrder(order)
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
fun DeliveringOrder(orderViewModel: OrderFoodViewModel) {
    val orderList by orderViewModel.orderFoodStateFlow.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(orderList) { order ->
            if (order.comfirm && !order.delivered && !order.cancelled) {
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
                        horizontalArrangement = Arrangement.End
                    ) {
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
                        items(order.listFood) { food ->
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
                                    text = food.title.toString(),
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
                            .padding(end = 4.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = "Tổng: ${order.sumPrice}đ",
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
                            onClick = { /*TODO*/ },
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
fun DeliveredOrder(orderViewModel: OrderFoodViewModel) {
    val orderList by orderViewModel.orderFoodStateFlow.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(orderList) { order ->
            if (order.comfirm && order.delivered && !order.cancelled) {
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
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = stringResource(id = R.string.delivered_order),
                            nomalColor = colorResource(id = R.color.red),
                            nomalFontsize = 16.sp
                        )
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        items(order.listFood) { food ->
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
                                    text = food.title.toString(),
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
                            .padding(end = 4.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = "Tổng: ${order.sumPrice}đ",
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
                            onClick = { /*TODO*/ },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.red),
                            ),
                            modifier = Modifier.padding(end = 4.dp, bottom = 2.dp)
                        ) {
                            NormalTextComponents(value = stringResource(R.string.comment_order))
                        }
                        Button(
                            onClick = { /*TODO*/ },
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

@Composable
fun CanceledOrder(orderViewModel: OrderFoodViewModel) {
    val orderList by orderViewModel.orderFoodStateFlow.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_background)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(orderList) { order ->
            if (order.cancelled) {
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
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = stringResource(id = R.string.cancel_order),
                            nomalColor = colorResource(id = R.color.red),
                            nomalFontsize = 16.sp
                        )
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        items(order.listFood) { food ->
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
                                    text = food.title.toString(),
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
                            .padding(end = 4.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = "Tổng: ${order.sumPrice}đ",
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
                            onClick = { /*TODO*/ },
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