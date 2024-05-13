package com.example.fooddelivery.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.viewmodel.SharedViewModel

@Composable
fun CartScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    innerPaddingValues: PaddingValues
) {
    val foodDetailStateFlow = sharedViewModel.foodDetailStateFlow.collectAsState()
    val foodDetailsList = foodDetailStateFlow.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                NormalTextComponents(
                    value = stringResource(R.string.cart_title),
                    nomalColor = Color.Black,
                    nomalFontsize = 24.sp,
                    nomalFontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        )
        {
            items(foodDetailStateFlow.value) { food ->
                val index = foodDetailStateFlow.value.indexOf(food)
                CardFoodIemWithCart(
                    foodDetails = food,
                    sharedViewModel = sharedViewModel,
                    index = index
                )
            }
        }
        NormalTextComponents(
            value = "Mã giảm giá",
            nomalColor = Color.Black,
            nomalFontsize = 24.sp,
            nomalFontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        NormalTextComponents(
            value = stringResource(R.string.order_summary),
            nomalColor = Color.Black,
            nomalFontsize = 24.sp,
            nomalFontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
                .background(
                    Color.LightGray.copy(alpha = 0.2f)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NormalTextComponents(
                        value = "Tổng phụ",
                        nomalColor = Color.Black,
                        nomalFontsize = 18.sp,
                        nomalFontWeight = FontWeight.Normal
                    )
                    NormalTextComponents(
                        value = "${sharedViewModel.caculatorPrice()}đ",
                        nomalColor = Color.Black,
                        nomalFontsize = 18.sp,
                        nomalFontWeight = FontWeight.Normal
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NormalTextComponents(
                        value = "Phí giao hàng", nomalColor = Color.Black,
                        nomalFontsize = 18.sp,
                        nomalFontWeight = FontWeight.Normal
                    )
                    NormalTextComponents(
                        value = "15000đ", nomalColor = Color.Black,
                        nomalFontsize = 18.sp,
                        nomalFontWeight = FontWeight.Normal
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    thickness = 1.dp,
                    color = Color.Blue
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NormalTextComponents(
                        value = "Tổng", nomalColor = Color.Black,
                        nomalFontsize = 18.sp,
                        nomalFontWeight = FontWeight.Bold
                    )
                    NormalTextComponents(
                        value = "${sharedViewModel.caculatorPrice() + 15000}đ",
                        nomalColor = Color.Black,
                        nomalFontsize = 18.sp,
                        nomalFontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                NormalTextComponents(
                    value = stringResource(R.string.place_order),
                    nomalFontWeight = FontWeight.Bold,
                    nomalFontsize = 24.sp,
                )
            }
        }
    }
}

@Composable
fun CardFoodIemWithCart(
    foodDetails: FoodDetails,
    index: Int,
    sharedViewModel: SharedViewModel
) {
    var quantity by remember {
        mutableIntStateOf(foodDetails.quantity)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(0.2f))
            .padding(vertical = 8.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = foodDetails.imagePath,
                contentDescription = foodDetails.title,
                modifier = Modifier.size(height = 100.dp, width = 130.dp),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 100.dp)
                    .padding(start = 8.dp, top = 6.dp, bottom = 6.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                NormalTextComponents(
                    value = foodDetails.title.toString(),
                    nomalFontsize = 18.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "${foodDetails.price}đ",
                    nomalFontsize = 16.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 6.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = stringResource(
                            R.string.minus
                        ),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                if (quantity > 0) {
                                    quantity--
                                    sharedViewModel.updateFoodDetailQuantity(
                                        index = index,
                                        newQuantity = quantity
                                    )
                                }
                            }
                    )
                    NormalTextComponents(
                        value = "${foodDetails.quantity}",
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
                                quantity++
                                sharedViewModel.updateFoodDetailQuantity(
                                    index = index,
                                    newQuantity = quantity
                                )
                            },
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        NormalTextComponents(
                            value = "${quantity * foodDetails.price}đ",
                            nomalFontWeight = FontWeight.Bold,
                            nomalFontsize = 18.sp,
                            nomalColor = Color.Red
                        )
                    }
                }
            }
        }
    }
}
