package com.example.fooddelivery.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.DiscountCode
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.model.Location
import com.example.fooddelivery.data.model.Price
import com.example.fooddelivery.data.model.Time
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.navigation.HomeRouteScreen
import java.text.DecimalFormat

@Composable
fun NormalTextComponents(
    modifier: Modifier = Modifier,
    value: String,
    nomalColor: Color = MaterialTheme.colorScheme.onPrimary,
    nomalFontsize: TextUnit = 14.sp,
    nomalFontWeight: FontWeight = FontWeight.Normal,
    nomalTextAlign: TextAlign = TextAlign.Center,
    nomalFontFamily: FontFamily = FontFamily.Default
) {
    Text(
        text = value,
        style = TextStyle(
            color = nomalColor,
            fontSize = nomalFontsize,
            fontWeight = nomalFontWeight,
            fontFamily = nomalFontFamily
        ),
        modifier = modifier,
        textAlign = nomalTextAlign
    )
}

@Composable
fun MyTextFieldComponents(
    lableText: String,
    errorStatus: Boolean = false,
    onTextSelected: (String) -> Unit
) {
    var textValue by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = textValue, onValueChange = {
            textValue = it
            onTextSelected(it)
        },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp),
        label = {
            Text(text = lableText)
        },
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Blue,
            focusedIndicatorColor = Color.Blue,
            disabledIndicatorColor = Color.Red,
            disabledLeadingIconColor = Color.Red,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black,
            disabledLabelColor = Color.Red,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Email_login")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = !errorStatus
    )
}

@Composable
fun MyPasswordTextFieldComponents(
    lableText: String,
    errorStatus: Boolean = false,
    onTextSelected: (String) -> Unit
) {
    var textPassWord by remember {
        mutableStateOf("")
    }
    var passWordVisibility by remember {
        mutableStateOf(false)
    }
    val iconVisibility = if (passWordVisibility) {
        painterResource(id = R.drawable.visibility)
    } else {
        painterResource(id = R.drawable.unvisible)
    }
    val iconDescription = if (passWordVisibility) {
        stringResource(id = R.string.Show_password)
    } else {
        stringResource(id = R.string.Hide_pass_word)
    }
    val localFocusManager = LocalFocusManager.current
    OutlinedTextField(
        value = textPassWord,
        onValueChange = {
            textPassWord = it
            onTextSelected(it)
        },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp),
        label = {
            Text(
                text = lableText
            )
        },
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Blue,
            focusedIndicatorColor = Color.Blue,
            disabledIndicatorColor = Color.Red,
            disabledTrailingIconColor = Color.Red,
            focusedTrailingIconColor = Color.Black,
            unfocusedTrailingIconColor = Color.Black,
            disabledLabelColor = Color.Red,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = {
                passWordVisibility = !passWordVisibility
            }) {
                Icon(
                    painter = iconVisibility, contentDescription = iconDescription,
                    modifier = Modifier.width(30.dp)
                )
            }
        },
        visualTransformation = if (passWordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                localFocusManager.clearFocus()
            }
        ),
        isError = !errorStatus
    )
}

@Composable
fun ButtonComponents(
    value: String, modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    onButtonClicked: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 32.dp, start = 55.dp, end = 55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
        enabled = isEnable,
        onClick = {
            onButtonClicked.invoke()
        }) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun DrawLineAndTextComponents() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp, start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp,
            color = Color.Red
        )
        NormalTextComponents(
            value = stringResource(id = R.string.or),
            nomalFontsize = 18.sp,
            nomalFontWeight = FontWeight.Normal,
            nomalColor = Color.Black
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp,
            color = Color.Red
        )
    }
}

@Composable
fun IconButtonWithText(
    backgroundColor: Color = colorResource(id = R.color.btn_1),
    @DrawableRes iconId: Int = R.drawable.btn_1,
    @StringRes textId: Int = R.string.Pizza,
    eventOnclick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = backgroundColor)
                .padding(12.dp)
        ) {
            IconButton(onClick = { eventOnclick() }) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = stringResource(
                        id = R.string.Pizza
                    )
                )
            }
        }
        NormalTextComponents(
            value = stringResource(id = textId),
            nomalFontsize = 18.sp,
            nomalColor = Color.Black,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun FoodItem(
    modifier: Modifier = Modifier,
    buttonSize: TextUnit = 30.sp,
    spacerbuttonModifier: Modifier = Modifier.padding(start = 0.dp),
    food: Food, navController: NavController,
    sharedViewModel: SharedViewModel = viewModel(),
) {
    val formatter = DecimalFormat("#.##")
    val decimalFormat = DecimalFormat("#,###.##")
    if (food.show) {
        Card(
            modifier = modifier
                .size(width = 260.dp, height = 295.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .clip(shape = RoundedCornerShape(15.dp))
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable {
                    navController.navigate(
                        route = HomeRouteScreen.ShopRouteScreen.sendIdShop(
                            food.idShop
                        )
                    )
                }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                AsyncImage(
                    model = food.ImagePath,
                    contentDescription = food.Title,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 180.dp)
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
                            value = food.Title.toString(),
                            nomalColor = Color.Black,
                            nomalFontsize = 18.sp,
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
                            value = "${decimalFormat.format(food.Price)}đ",
                            nomalColor = Color.Black,
                            nomalFontWeight = FontWeight.Bold,
                            nomalFontsize = 18.sp,
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                            ) {
                                NormalTextComponents(
                                    value = formatter.format(food.Star),
                                    nomalColor = Color.Black,
                                    nomalFontsize = 18.sp,
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = stringResource(
                                        R.string.star_icon
                                    ),
                                    tint = Color.Yellow,
                                    modifier = Modifier.scale(1.8f)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                NormalTextComponents(
                                    value = "${food.TimeValue}p",
                                    nomalFontsize = 18.sp,
                                    nomalColor = Color.Black,
                                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.time),
                                    contentDescription = stringResource(
                                        id = R.string.time
                                    ),
                                    modifier = Modifier
                                        .scale(1.8f)
                                        .padding(start = 8.dp, bottom = 8.dp),
                                    tint = Color.Red
                                )
                            }
                            Spacer(modifier = spacerbuttonModifier)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Button(colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                ), shape = RectangleShape, modifier = Modifier.clip(
                                    shape = RoundedCornerShape(
                                        topStart = 15.dp, bottomEnd = 16.dp
                                    )
                                ), onClick = {
                                    val fooddetails = FoodDetails(
                                        title = food.Title.toString(),
                                        imagePath = food.ImagePath.toString(),
                                        price = food.Price.toFloat(),
                                        quantity = 1,
                                        id = food.Id
                                    )

                                    /*Toast.makeText(
                                        context, "Thêm vào giỏ hàng thành công",
                                        Toast.LENGTH_SHORT
                                    ).show()*/
                                    sharedViewModel.getIdShop(food.idShop)
                                    sharedViewModel.addFoodDetail(foodDetails = fooddetails)
                                    navController.navigate(
                                        route = HomeRouteScreen.ShopRouteScreen.sendIdShop(
                                            food.idShop
                                        )
                                    )
                                }) {
                                    Text(
                                        text = "+", style = TextStyle(
                                            fontSize = buttonSize, textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropdownMenuWithLoc(modifier: Modifier = Modifier, location: MutableList<Location>) {
    val selected = remember {
        mutableStateOf(location[0].loc)
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
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand.value)
                },
                modifier = Modifier.menuAnchor(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = stringResource(
                            id = R.string.location_icon
                        ), modifier = Modifier.scale(1.5f)
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expand.value,
                onDismissRequest = { expand.value = false }) {
                location.forEach {
                    DropdownMenuItem(text = {
                        Text(text = it.loc.toString())
                    }, onClick = {
                        selected.value = it.loc
                        expand.value = false
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropdownMenuWithTime(modifier: Modifier = Modifier, time: MutableList<Time>) {
    val selected = remember {
        mutableStateOf(time[0].Value)
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
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand.value)
                },
                singleLine = true,
                modifier = Modifier.menuAnchor(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = stringResource(
                            id = R.string.time
                        ),
                        modifier = Modifier.scale(1.5f)
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expand.value,
                onDismissRequest = { expand.value = false }) {
                time.forEach {
                    DropdownMenuItem(text = {
                        Text(text = it.Value.toString())
                    }, onClick = {
                        selected.value = it.Value
                        expand.value = false
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropdownMenuWithPrice(modifier: Modifier = Modifier, price: MutableList<Price>) {
    val selected = remember {
        mutableStateOf(price[0].Value)
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
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand.value)
                },
                modifier = Modifier.menuAnchor(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.dollar),
                        contentDescription = stringResource(
                            id = R.string.price
                        ),
                        modifier = Modifier.scale(1.5f),
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expand.value,
                onDismissRequest = { expand.value = false }) {
                price.forEach {
                    DropdownMenuItem(text = {
                        Text(text = it.Value.toString())
                    }, onClick = {
                        selected.value = it.Value
                        expand.value = false
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropdownMenuWithDiscountCode(
    modifier: Modifier = Modifier, discountcode: MutableList<DiscountCode>,
    sharedViewModel: SharedViewModel
) {
    val valueDiscount = remember {
        mutableFloatStateOf(discountcode.firstOrNull()?.value ?: 0f)
    }
    val selected = remember {
        mutableStateOf(discountcode.firstOrNull()?.name ?: "")
    }
    val expand = remember {
        mutableStateOf(false)
    }
    sharedViewModel.getDiscountCodeValue(value = valueDiscount.floatValue)
    Box(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expand.value, onExpandedChange = {
                expand.value = !expand.value
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selected.value, onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand.value)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.dollar),
                        contentDescription = stringResource(
                            id = R.string.price
                        ),
                        modifier = Modifier.scale(1.5f),
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expand.value,
                onDismissRequest = { expand.value = false }) {
                discountcode.forEach {
                    DropdownMenuItem(text = {
                        Text(text = it.name)
                    }, onClick = {
                        selected.value = it.name
                        expand.value = false
                        valueDiscount.floatValue = it.value
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

@Composable
fun ExpandableTextField(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onDone: (Boolean) -> Unit,
    onCancel: (Boolean) -> Unit,
    saveText: String
) {
    val localFocusManager = LocalFocusManager.current
    var noteTextField by remember {
        mutableStateOf(saveText)
    }
    val wordCount = noteTextField.length
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.onSecondary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray
                    )
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray.copy(alpha = 1f)
                    ),
                    modifier = Modifier.clickable { onCancel(false) }
                )
                Text(
                    text = stringResource(R.string.add_note_order),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.done),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.Red
                    ),
                    modifier = Modifier.clickable { onDone(false) }
                )
            }
            TextField(
                value = noteTextField,
                onValueChange = {
                    if (wordCount < 100) {
                        noteTextField = it
                        onTextChange(noteTextField)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "$wordCount/100",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray.copy(alpha = 1f)
                    )
                )
            }
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
fun ComponentPreview() {

}