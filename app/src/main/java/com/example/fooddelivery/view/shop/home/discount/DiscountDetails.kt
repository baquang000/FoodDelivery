package com.example.fooddelivery.view.shop.home.discount

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.data.viewmodel.shop.DiscountViewModel
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscountDetailScreen(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    discountViewModel: DiscountViewModel = viewModel()
) {
    val id = navBackStackEntry.arguments?.getInt(ID_ARGUMENT_KEY) ?: 0
    LaunchedEffect(key1 = id) {
        this.launch(Dispatchers.IO) {
            discountViewModel.getDiscountDetails(id)
        }
    }
    val discountDetail by discountViewModel.discountDetails.collectAsStateWithLifecycle()
    val isloading by discountViewModel.isLoadDiscountDetails.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Chi tiết mã giảm giá",
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
        // Nội dung của màn hình
        if (isloading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(
                    vertical = 100.dp,
                    horizontal = 100.dp
                )
            )
        } else {
            discountDetail?.let { discount ->
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    OutlineTextFieldDiscount(
                        text = discount.name,
                        textColor = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        lable = "Tên mã khuyến mãi"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlineTextFieldDiscount(
                            text = "${discount.minOrderAmount} đ",
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Giá trị đơn tối thiểu"
                        )
                        OutlineTextFieldDiscount(
                            text = "${discount.maxDiscountAmount} đ",
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Giảm tối đa"
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlineTextFieldDiscount(
                            text = discount.startDate,
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Ngày bắt đầu"
                        )
                        OutlineTextFieldDiscount(
                            text = discount.endDate,
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Ngày kết thúc"
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlineTextFieldDiscount(
                            text = "${discount.maxUser}",
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Số người được dùng"
                        )
                        OutlineTextFieldDiscount(
                            text = "${discount.numberUse} lần",
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Mỗi người dùng"
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlineTextFieldDiscount(
                            text = discount.typeDiscount,
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Kiểu giảm giá"
                        )
                        OutlineTextFieldDiscount(
                            text = discount.percentage,
                            textColor = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            lable = "Giá trị giảm giá "
                        )
                    }
                    OutlineTextFieldDiscount(
                        text = discount.description,
                        textColor = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        lable = "Chi tiết mã giảm giá"
                    )
                }
            }
        }
    }
}

@Composable
fun OutlineTextFieldDiscount(
    text: String,
    lable: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    outlineColor: Color = Color.Gray,
    enabled: Boolean = false,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    OutlinedTextField(
        value = text, onValueChange = {},
        label = {
            Text(text = lable, color = Color.Gray)
        },
        textStyle = TextStyle(
            color = textColor,
            fontWeight = fontWeight,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = outlineColor,
            disabledContainerColor = Color.White
        ),
        enabled = enabled,
        modifier = modifier.padding(vertical = 12.dp, horizontal = 12.dp),
    )
}

/*@Preview
@Composable
fun PreviewDiscount() {
    DiscountDetailScreen()
}*/
