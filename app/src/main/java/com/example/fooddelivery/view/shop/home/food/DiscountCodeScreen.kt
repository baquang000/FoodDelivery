package com.example.fooddelivery.view.shop.home.food

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.shop.NormalTextComponents
import com.example.fooddelivery.data.model.GetDiscountItem
import com.example.fooddelivery.data.viewmodel.shop.DiscountViewModel

@Composable
fun DiscountCodeScreen(
    navController: NavController,
    discountViewModel: DiscountViewModel = viewModel()
) {
    val discountCode by discountViewModel.discount.collectAsStateWithLifecycle()
    val isLoading by discountViewModel.isLoadDiscount.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    HeadingDiscountScreen(navController = navController)
                }
                items(discountCode) { discount ->
                    BodyDiscountScreen(discount = discount)
                }
            }
        }
    }
}

@Composable
fun HeadingDiscountScreen(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp, top = 17.dp),
        verticalAlignment = Alignment.Top,
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
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            NormalTextComponents(
                value = stringResource(R.string.discount_code),
                nomalFontsize = 28.sp,
                nomalColor = colorResource(id = R.color.black),
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }
}

@Composable
fun BodyDiscountScreen(
    discount: GetDiscountItem
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
            .background(Color.LightGray.copy(alpha = 0.5f)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        DiscountItem(lable = "Tên : ", name = discount.name)
        DiscountItem(lable = "Loại mã giảm giá : ", name = discount.typeDiscount.toString())
        DiscountItem(lable = "Mức mã giảm giá : ", name = discount.percentage)
        DiscountItem(lable = "Giá trị tối thiểu : ", name = discount.minOrderAmount)
        DiscountItem(lable = "Giá trị tối đa : ", name = discount.maxDiscountAmount)
        DiscountItem(lable = "Thời gian bắt đầu : ", name = discount.startDate)
        DiscountItem(lable = "Thời gian kết thúc : ", name = discount.endDate)
        DiscountItem(lable = "Trạng thái : ", name = discount.isActive.toString())
        DiscountItem(lable = "Chi tiết : ", name = discount.description)
    }
}


@Composable
fun DiscountItem(
    lable: String,
    name: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = lable, style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            )
        )
        Text(
            text = name, style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            )
        )
    }
}

