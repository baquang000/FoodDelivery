package com.example.fooddelivery.view.shop.home.discount

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.GetDiscountItem
import com.example.fooddelivery.data.viewmodel.shop.DiscountViewModel
import com.example.fooddelivery.navigation.ShopRouteScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscountCodeScreen(
    navController: NavController,
    discountViewModel: DiscountViewModel = viewModel()
) {
    val discountCode by discountViewModel.discount.collectAsStateWithLifecycle()
    val isLoading by discountViewModel.isLoadDiscount.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.discount_code),
                        color = Color.White,
                        modifier = Modifier.padding(start = 45.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green
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
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Text(
                            text = "Đang hoạt động", style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                        )
                    }
                    items(discountCode) { discount ->
                        if (discount.isActive) {
                            BodyDiscountScreen(discount = discount, onClick = {
                                navController.navigate(
                                    ShopRouteScreen.DiscountDetail.sendId(
                                        discount.id
                                    )
                                )
                            })
                        }
                    }
                    item {
                        Text(
                            text = "Dừng hoạt động", style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                        )
                    }
                    items(discountCode) { discount ->
                        if (!discount.isActive) {
                            BodyDiscountScreen(discount = discount, onClick = {
                                navController.navigate(
                                    ShopRouteScreen.DiscountDetail.sendId(
                                        discount.id
                                    )
                                )
                            })
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = ShopRouteScreen.AddDiscountCode.route)
                },
                containerColor = Color.Blue,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}


@Composable
fun BodyDiscountScreen(
    modifier: Modifier = Modifier,
    discount: GetDiscountItem,
    paddingCard: Dp = 12.dp,
    paddingRow : Dp = 10.dp,
    paddingColumn : Dp = 14.dp,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (discount.isActive) Color.Green else Color.Gray
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = paddingCard, vertical = paddingCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = paddingRow),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = paddingColumn)
            ) {
                Text(
                    text = discount.name, style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(250.dp)
                )
                Text(
                    text = discount.description, style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(250.dp)
                )
                Text(
                    text = "${discount.isActive}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = if (discount.isActive) Color.Green else Color.Gray,
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
            Button(
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow1),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    tint = if (discount.isActive) Color.Green else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun DiscountPreview() {
//    BodyDiscountScreen()
//}

