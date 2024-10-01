@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.fooddelivery.view.home

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooddelivery.R
import com.example.fooddelivery.components.ExpandableTextField
import com.example.fooddelivery.components.MyDropdownMenuWithDiscountCode
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.DiscountCodeState
import com.example.fooddelivery.data.model.FoodDetails
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.profileviewmodel.UserInforViewModel
import com.example.fooddelivery.navigation.HomeRouteScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.time.Duration.Companion.seconds


@Composable
fun CartScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    userInforViewModel: UserInforViewModel,
    innerPaddingValues: PaddingValues,
) {
    val context = LocalContext.current
    val foodDetailStateFlow = sharedViewModel.foodDetailStateFlow.collectAsStateWithLifecycle()
    val foodDetailsList = foodDetailStateFlow.value
    val sumPrice = sharedViewModel.sumPrice.collectAsState()
    val isErrorDelete by sharedViewModel::isErrorDelete
    val discountValue by sharedViewModel.discountCodeValue.collectAsStateWithLifecycle()
    val totalPrice = foodDetailStateFlow.value.sumOf { it.price * it.quantity.toDouble() }.toFloat()
    //userInfor
    val userInfor by userInforViewModel.userInfor.collectAsStateWithLifecycle()
    var name by remember {
        mutableStateOf("")
    }
    var numberphone by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var dateOfBirth by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = userInfor) {
        userInfor?.let {
            name = it.name.toString()
            numberphone = it.numberPhone.toString()
            address = it.address.toString()
            email = it.email
            dateOfBirth = it.dateOfBirth.toString()
        }
    }

    //switch giao hang tan cua
    var switchDeliverytoDoor by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = switchDeliverytoDoor) {
        sharedViewModel.deliverytoDoorChange(switchDeliverytoDoor)
    }
    //switch lay dung cu an uong
    var switchDiningSubtances by remember {
        mutableStateOf(true)
    }
    val decimalFomat = DecimalFormat("#,###.##")
    //text option , Tip for driver
    var textOption by remember {
        mutableStateOf("Chưa có")
    }
    var valueTips by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = valueTips) {
        sharedViewModel.rewardForDriverChange(valueTips)
    }
    // note order
    var openNoteOrder by remember {
        mutableStateOf(false)
    }
    var textNoteOrder by remember {
        mutableStateOf("")
    }
    var cancelOrDoneOrder by remember {
        mutableStateOf<Boolean?>(null)
    }
    // Tinh chieu cao screen
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val halfScreenHeight = screenHeight / 2
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray.copy(alpha = 0.2f))
                .padding(innerPaddingValues)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(color = MaterialTheme.colorScheme.onSecondary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
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
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(color = MaterialTheme.colorScheme.onSecondary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = stringResource(
                                id = R.string.location
                            ),
                            modifier = Modifier.size(width = 18.dp, height = 18.dp),
                            tint = Color.Red
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(7f)
                    ) {
                        Text(
                            text = stringResource(R.string.location_order),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "$name | $numberphone",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = address, style = MaterialTheme.typography.titleMedium)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 8.dp, start = 8.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.next_button),
                            contentDescription = stringResource(
                                id = R.string.next_button
                            ),
                            modifier = Modifier.size(width = 24.dp, height = 24.dp)
                        )
                    }
                }
            }
            items(foodDetailStateFlow.value, key = {
                it.idFood!!
            }) { food ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 12.dp)
                ) {
                    SwipeToDismissItem(
                        foodDetails = food,
                        sharedViewModel = sharedViewModel,
                        onRemove = {
                            sharedViewModel.deleteItemFoodInCart(food)
                            if (isErrorDelete) {
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.animateItem(
                            fadeInSpec = null,
                            fadeOutSpec = null,
                            placementSpec = tween(200)
                        )
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.onSecondary)
                        .padding(bottom = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
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
                                    value = stringResource(R.string.sum_price_order),
                                    nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
                                    nomalFontWeight = FontWeight.Normal
                                )
                                NormalTextComponents(
                                    value = "${decimalFomat.format(sharedViewModel.caculatorPrice())}đ",
                                    nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
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
                                    value = stringResource(R.string.price_ship),
                                    nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
                                    nomalFontWeight = FontWeight.Normal
                                )
                                NormalTextComponents(
                                    value = "${decimalFomat.format(15000)}đ",
                                    nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
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
                                    value = stringResource(R.string.tax_order),
                                    nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
                                    nomalFontWeight = FontWeight.Normal
                                )
                                NormalTextComponents(
                                    value = if (switchDeliverytoDoor) "8,000" else "3,000",
                                    nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
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
                                    value = stringResource(R.string.discount_code_order),
                                    nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
                                    nomalFontWeight = FontWeight.Normal
                                )
                                NormalTextComponents(
                                    value = if (discountValue == 15000f) {
                                        "15,000đ"
                                    } else {
                                        "${decimalFomat.format(discountValue * totalPrice)}đ"
                                    }, nomalColor = Color.Black,
                                    nomalFontsize = 16.sp,
                                    nomalFontWeight = FontWeight.Normal
                                )
                            }
                            if (valueTips == 5000 || valueTips == 10000 || valueTips == 15000) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    NormalTextComponents(
                                        value = stringResource(id = R.string.reward_for_driver),
                                        nomalColor = Color.Black,
                                        nomalFontsize = 16.sp,
                                        nomalFontWeight = FontWeight.Normal
                                    )
                                    NormalTextComponents(
                                        value = "${decimalFomat.format(valueTips)}đ",
                                        nomalColor = Color.Black,
                                        nomalFontsize = 16.sp,
                                        nomalFontWeight = FontWeight.Normal
                                    )
                                }
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
                                    value = stringResource(id = R.string.sum_price_order),
                                    nomalColor = Color.Black,
                                    nomalFontsize = 20.sp,
                                    nomalFontWeight = FontWeight.Bold
                                )
                                NormalTextComponents(
                                    value = "${decimalFomat.format(sumPrice.value)}đ",
                                    nomalColor = Color.Red,
                                    nomalFontsize = 20.sp,
                                    nomalFontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                NormalTextComponents(
                                    value = "Đã bao gồm thuế",
                                    nomalColor = Color.Gray,
                                    nomalFontsize = 18.sp,
                                    nomalFontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
            item {
                SetDiscountCodeItems(
                    sharedViewModel = sharedViewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, top = 12.dp)
                        .background(color = MaterialTheme.colorScheme.onSecondary)
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onSecondary)
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.money),
                            contentDescription = stringResource(
                                R.string.tips_icon
                            ),
                            tint = Color.Yellow,
                            modifier = Modifier.size(width = 30.dp, height = 30.dp)
                        )
                        Text(
                            text = stringResource(R.string.reward_for_driver),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        OptionButton(
                            "Chưa có",
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                                .clickable {
                                    textOption = "Chưa có"
                                    valueTips = 0
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (textOption == "Chưa có") Color.Red else Color.Transparent
                                )
                        )
                        OptionButton(
                            "5k",
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                                .clickable {
                                    textOption = "5k"
                                    valueTips = 5000
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (textOption == "5k") Color.Red else Color.Transparent
                                )
                        )
                        OptionButton(
                            "10k",
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                                .clickable {
                                    textOption = "10k"
                                    valueTips = 10000
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (textOption == "10k") Color.Red else Color.Transparent
                                )
                        )
                        OptionButton(
                            "15k",
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                                .clickable {
                                    textOption = "15k"
                                    valueTips = 15000
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (textOption == "15k") Color.Red else Color.Transparent
                                )
                        )
                        OptionButton(
                            "Khác",
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .clickable {
                                    textOption = "Khác"
                                    valueTips = 0
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (textOption == "Khác") Color.Red else Color.Transparent
                                )
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.door_to_door),
                                contentDescription = stringResource(
                                    R.string.delivery_to_door
                                ),
                                tint = Color.Red,
                                modifier = Modifier.size(width = 30.dp, height = 30.dp)
                            )
                            Text(
                                text = stringResource(R.string.delivery_to_door),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = "[5,000đ]",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Switch(
                                checked = switchDeliverytoDoor, onCheckedChange = {
                                    switchDeliverytoDoor = it
                                },
                                modifier = Modifier.size(24.dp, 24.dp),
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.onSecondary,
                                    checkedTrackColor = Color.Green,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.outline.copy(
                                        alpha = 0.2f
                                    ),
                                    uncheckedBorderColor = MaterialTheme.colorScheme.outline.copy(
                                        alpha = 0.2f
                                    )
                                )
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.spoon_and_fork_crossed),
                                    contentDescription = stringResource(
                                        R.string.take_dining_subtances
                                    ),
                                    tint = Color.Red,
                                    modifier = Modifier.size(width = 30.dp, height = 30.dp)
                                )
                                Text(
                                    text = stringResource(R.string.take_dining_subtances),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Switch(
                                    checked = switchDiningSubtances, onCheckedChange = {
                                        switchDiningSubtances = it
                                    },
                                    modifier = Modifier.size(24.dp, 24.dp),
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                                        uncheckedThumbColor = MaterialTheme.colorScheme.onSecondary,
                                        checkedTrackColor = Color.Green,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.outline.copy(
                                            alpha = 0.2f
                                        ),
                                        uncheckedBorderColor = MaterialTheme.colorScheme.outline.copy(
                                            alpha = 0.2f
                                        )
                                    )
                                )
                            }
                        }
                        Text(
                            text = stringResource(R.string.desciption_dining_subtances),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                            textAlign = TextAlign.Justify
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.note),
                                contentDescription = stringResource(
                                    R.string.note_order
                                ),
                                tint = Color.Yellow,
                                modifier = Modifier.size(width = 30.dp, height = 30.dp)
                            )
                            Text(
                                text = stringResource(R.string.note_order),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.clickable {
                                openNoteOrder = !openNoteOrder
                            }
                        ) {
                            Text(
                                text = if (cancelOrDoneOrder == true) {
                                    if (textNoteOrder.length > 10) textNoteOrder.take(10) + "..."
                                    else textNoteOrder
                                } else stringResource(
                                    R.string.note_order
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.next_button),
                                contentDescription = stringResource(
                                    R.string.next_button
                                ),
                                tint = Color.Black,
                                modifier = Modifier.size(width = 30.dp, height = 30.dp)
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (foodDetailsList.isEmpty() || sharedViewModel.caculatorPrice() == 0.0) {
                                Toast.makeText(
                                    context,
                                    "Chưa có món ăn nào trong giỏ hàng",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                sharedViewModel.createOrderAndDetails(
                                    noteOrder = textNoteOrder,
                                    rewardForDriver = valueTips,
                                    deliverytoDoor = switchDeliverytoDoor,
                                    diningSubtances = switchDiningSubtances
                                )
                                Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate(route = HomeRouteScreen.Home.route) {
                                    popUpTo(HomeRouteScreen.Home.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        },
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
        if (openNoteOrder) {
            AnimatedContent(
                targetState = true,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray.copy(0.5f)),
                content = { open ->
                    if (open) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Transparent)
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .height(halfScreenHeight)
                            )
                            ExpandableTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(halfScreenHeight),
                                onDone = {
                                    openNoteOrder = it
                                    cancelOrDoneOrder = true
                                },
                                onCancel = {
                                    openNoteOrder = it
                                    cancelOrDoneOrder = false
                                },
                                onTextChange = {
                                    textNoteOrder = it
                                },
                                saveText = textNoteOrder
                            )
                        }
                    }
                },
                transitionSpec = {
                    slideInVertically(
                        initialOffsetY = {
                            if (openNoteOrder) it else -it
                        }
                    ) togetherWith slideOutVertically(
                        targetOffsetY = {
                            if (openNoteOrder) -it else it
                        }
                    )
                }, label = "note_order"
            )
        }
    }
}

@Composable
fun OptionButton(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
            )
            .size(36.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun CardFoodIemWithCart(
    foodDetails: FoodDetails,
    sharedViewModel: SharedViewModel
) {
    var quantity by remember {
        mutableIntStateOf(foodDetails.quantity)
    }
    val decimalFormat = DecimalFormat("#,###.##")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onSecondary)
            .padding(vertical = 8.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.onSecondary)
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
                    value = "${decimalFormat.format(foodDetails.price)}đ",
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
                                        id = foodDetails.idFood!!,
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
                                    id = foodDetails.idFood!!,
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
                            value = "${decimalFormat.format(quantity * foodDetails.price)}đ",
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

@ExperimentalMaterial3Api
@Composable
fun SwipeToDismissItem(
    foodDetails: FoodDetails,
    sharedViewModel: SharedViewModel,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                coroutineScope.launch {
                    delay(1.seconds)
                    onRemove()
                }
                true
            } else false
        }
    )
    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            val backgroundColor by animateColorAsState(
                targetValue = when (swipeToDismissState.currentValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                }, label = "Animate bg color"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.trash),
                    contentDescription = stringResource(
                        R.string.trash_icon
                    ),
                    modifier = Modifier.size(width = 48.dp, 48.dp),
                    tint = Color.Red
                )
            }
        },
        modifier = modifier
    ) {
        CardFoodIemWithCart(
            foodDetails = foodDetails,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun SetDiscountCodeItems(sharedViewModel: SharedViewModel, modifier: Modifier = Modifier) {
    when (val result = sharedViewModel.discountCode.value) {
        is DiscountCodeState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DiscountCodeState.Success -> {
            MyDropdownMenuWithDiscountCode(
                discountcode = result.data,
                modifier = modifier,
                sharedViewModel = sharedViewModel
            )
        }

        is DiscountCodeState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = result.message, style = TextStyle(
                        fontSize = 20.sp,
                    )
                )
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.error_loading_data), style = TextStyle(
                        fontSize = 20.sp, color = Color.Red
                    )
                )
            }
        }
    }
}
