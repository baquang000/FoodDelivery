package com.example.fooddelivery.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.R
import java.text.DecimalFormat


@Composable
fun CustomSnackBar(
    countFood: Int,
    price: Double,
    onAction: () -> Unit
) {
    Snackbar(containerColor = Color.White) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LeadingImageWithCount(countFood)
            ContentSnackBarInShop(price = price) {
                onAction.invoke()
            }
        }

    }
}

@Composable
fun LeadingImageWithCount(
    count: Int,
    image: Int = R.drawable.shopping
) {
    Row(
        modifier = Modifier.wrapContentSize()
    ) {
        Image(
            painterResource(id = image),
            contentDescription = "Image leading snack bar",
            modifier = Modifier.size(36.dp)
        )
        if (count > 0) {
            Text(
                text = count.toString(),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier // Canh giữa nội dung Text
            )
        }
    }
}

@Composable
fun ContentSnackBarInShop(
    price: Double,
    onAction: () -> Unit
) {
    val decimalFormat = DecimalFormat("#,###.##")

    Row(
        modifier = Modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "${decimalFormat.format(price)}đ", fontSize = 12.sp,
            color = Color.Red
        )
        Spacer(modifier = Modifier.width(6.dp))
        Button(
            onClick = {
                onAction.invoke()
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(text = "Giao hàng", fontSize = 12.sp, color = Color.White)
        }
    }
}

@Composable
fun LeadingImageWithCountInHome(
    count: Int,
    nameShop: String,
    price: Double,
    image: Int = R.drawable.menu,
    onAction: () -> Unit
) {
    val decimalFormat = DecimalFormat("#,###.##")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painterResource(id = image),
                contentDescription = "Image leading snack bar",
                modifier = Modifier.size(36.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = nameShop,
                    color = Color.Black,
                    fontSize = 12.sp,
                    modifier = Modifier
                )
                Text(
                    text = "$count món - ${decimalFormat.format(price)}đ",
                    color = Color.Black,
                    fontSize = 12.sp,
                )
            }
        }
        Image(
            painterResource(id = R.drawable.close),
            contentDescription = "close",
            modifier = Modifier
                .size(36.dp)
                .clickable { onAction.invoke() }
        )
    }
}

@Composable
fun CustomSnackBarInHome(
    countFood: Int,
    price: Double,
    nameShop: String,
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    Snackbar(containerColor = Color.White, modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LeadingImageWithCountInHome(count = countFood, nameShop = nameShop, price = price){
                onAction.invoke()
            }
        }
    }
}