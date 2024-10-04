package com.example.fooddelivery.components.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.viewmodel.shop.ViewAllViewModel
import com.example.fooddelivery.navigation.ShopRouteScreen
import kotlinx.coroutines.launch
import java.net.URLEncoder

@Composable
fun NormalTextComponents(
    modifier: Modifier = Modifier,
    value: String,
    nomalColor: Color = Color.White,
    nomalFontsize: TextUnit = 14.sp,
    nomalFontWeight: FontWeight = FontWeight.Normal,
    nomalTextAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = value,
        style = TextStyle(
            color = nomalColor,
            fontSize = nomalFontsize,
            fontWeight = nomalFontWeight
        ),
        modifier = modifier,
        textAlign = nomalTextAlign,
        onTextLayout = {}
    )
}


@Composable
fun FoodItem(
    modifier: Modifier = Modifier,
    food: Food, navController: NavController,
    viewAllViewModel: ViewAllViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var isBestFood by remember {
        mutableStateOf(food.bestFood)
    }
    var isShowFood by remember {
        mutableStateOf(food.showFood)
    }
    val encodeURL = URLEncoder.encode(food.imagePath, "UTF-8")
    Card(
        modifier = modifier
            .size(width = 260.dp, height = 320.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .clip(shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                navController.navigate(
                    ShopRouteScreen.FoodDetails.sendFood(
                        title = food.title,
                        price = food.price.price,
                        star = food.star.toDouble(),
                        timevalue = food.time.time,
                        description = food.description,
                        imagepath = encodeURL,
                        id = food.idFood
                    )
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model = food.imagePath,
                contentDescription = food.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 150.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    NormalTextComponents(
                        value = food.title,
                        nomalColor = Color.Black,
                        nomalFontsize = 14.sp,
                        nomalFontWeight = FontWeight.Bold,
                        nomalTextAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    NormalTextComponents(
                        value = "${food.price.price}đ",
                        nomalColor = Color.Black,
                        nomalFontWeight = FontWeight.Bold,
                        nomalFontsize = 14.sp,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 6.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            NormalTextComponents(
                                value = food.star,
                                nomalColor = Color.Black,
                                nomalFontsize = 14.sp,
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = stringResource(
                                    R.string.star_icon
                                ),
                                tint = Color.Yellow,
                                modifier = Modifier.size(width = 16.dp, height = 16.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            NormalTextComponents(
                                value = "${food.time.time}p",
                                nomalFontsize = 14.sp,
                                nomalColor = Color.Black,
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.time),
                                contentDescription = stringResource(
                                    id = R.string.time
                                ),
                                modifier = Modifier
                                    .size(width = 16.dp, height = 16.dp),
                                tint = Color.Red
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 2.dp, horizontal = 2.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NormalTextComponents(
                                value = stringResource(id = R.string.best_food),
                                nomalColor = colorResource(id = R.color.black),
                                nomalFontsize = 14.sp
                            )
                            Switch(checked = isBestFood, onCheckedChange = {
                                isBestFood = it
                                coroutineScope.launch {
                                    viewAllViewModel.updateIsBestFood(
                                        isBestFood = isBestFood,
                                        idFood = food.idFood
                                    )
                                }
                            }, modifier = Modifier.size(width = 5.dp, height = 2.dp))
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NormalTextComponents(
                                value = stringResource(R.string.show),
                                nomalColor = colorResource(id = R.color.black),
                                nomalFontsize = 14.sp
                            )
                            Switch(checked = isShowFood, onCheckedChange = {
                                isShowFood = it
                                coroutineScope.launch {
                                    viewAllViewModel.updateIsShowFood(
                                        showFood = isShowFood,
                                        id = food.idFood
                                    )
                                }
                            }, modifier = Modifier.size(width = 5.dp, height = 2.dp))
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MyDropdownMenuWithBestFood(
    modifier: Modifier = Modifier,
    list: List<T>,
    onSelectedIndex: (Int) -> Unit,
    onSelelectedValue: (T) -> Unit
) {
    val selected = remember {
        mutableStateOf(list[0])
    }
    val expand = remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier) {
        ExposedDropdownMenuBox(expanded = expand.value, onExpandedChange = {
            expand.value = !expand.value
        }) {
            OutlinedTextField(
                value = selected.value.toString(), onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    TrailingIcon(expanded = expand.value)
                },
                modifier = Modifier
                    .wrapContentSize()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expand.value,
                onDismissRequest = { expand.value = false }) {
                list.forEachIndexed { index, value ->
                    DropdownMenuItem(text = {
                        Text(text = value.toString())
                    }, onClick = {
                        selected.value = value
                        expand.value = false
                        onSelectedIndex(index)
                        onSelelectedValue(value)
                    })
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float = 0.0f,
    star: Int = 5,
    starColor: Color = Color.Yellow,
    onRatingChange: (Float) -> Unit
) {
    var isHalfStar = (rating % 1) != 0.0f
    Row {
        for (index in 1..star) {
            Icon(
                modifier = modifier.clickable { onRatingChange(index.toFloat()) },
                contentDescription = null,
                tint = starColor,
                imageVector = if (index <= rating) {
                    Icons.Rounded.Star
                } else {
                    if (isHalfStar) {
                        isHalfStar = false
                        Icons.AutoMirrored.Rounded.StarHalf
                    } else {
                        Icons.Rounded.StarOutline
                    }
                }
            )
        }
    }
}