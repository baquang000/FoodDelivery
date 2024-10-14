package com.example.fooddelivery.view.shop.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.viewmodel.shop.HomeViewModel
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.HomeRouteScreen
import com.example.fooddelivery.navigation.ShopRouteScreen
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun HomeShopScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val numIsConfirm by homeViewModel::numIsConfirm
    val deliveringOrder by homeViewModel::deliveringOrder
    val deliveredOrder by homeViewModel::countdeliveredOrder
    val cancelOrder by homeViewModel::cancelOrder
    val totalPrice by homeViewModel.totalPrice.collectAsStateWithLifecycle()
    val order by homeViewModel.orderStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = order) {
        homeViewModel.updateOrderCounts()
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    val decimalFormat = DecimalFormat("#,###.##")
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(
                    id = R.string.logo
                ),
                modifier = Modifier.size(width = 36.dp, height = 36.dp)
            )
            NormalTextComponents(
                value = stringResource(id = R.string.app_name),
                nomalColor = Color.Black,
                nomalFontsize = 40.sp,
            )
        }
        Text(
            text = "Thống kê theo ngày",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 120.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                .height(108.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color.Gray,
                    spotColor = Color.DarkGray,
                ), colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.card_color),
                disabledContainerColor = colorResource(id = R.color.card_color)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            navController.navigate(route = ShopRouteScreen.ComfirmOrder.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.exclamation_mark),
                        contentDescription = stringResource(
                            R.string.exclamation_mark
                        ),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp)
                    )
                    NormalTextComponents(
                        value = stringResource(R.string.pending_order),
                        nomalColor = Color.Red,
                        nomalFontsize = 20.sp
                    )
                    NormalTextComponents(
                        value = numIsConfirm.toString(),
                        nomalColor = Color.Red,
                        nomalFontsize = 18.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            navController.navigate(route = ShopRouteScreen.DeliveringOrder.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.delivering),
                        contentDescription = stringResource(R.string.revenu_image),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp)
                    )
                    NormalTextComponents(
                        value = stringResource(R.string.delevering_order),
                        nomalColor = Color.Black,
                        nomalFontsize = 20.sp
                    )
                    NormalTextComponents(
                        value = deliveringOrder.toString(),
                        nomalColor = Color.Black,
                        nomalFontsize = 18.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            navController.navigate(route = ShopRouteScreen.DeliveredOrder.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = stringResource(R.string.check_image),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp)
                    )
                    NormalTextComponents(
                        value = stringResource(R.string.complete_order),
                        nomalColor = Color.Green,
                        nomalFontsize = 20.sp
                    )
                    NormalTextComponents(
                        value = deliveredOrder.toString(),
                        nomalColor = Color.Green,
                        nomalFontsize = 18.sp
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                .height(108.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color.Gray,
                    spotColor = Color.DarkGray,
                ), colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.card_color),
                disabledContainerColor = colorResource(id = R.color.card_color)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            navController.navigate(route = ShopRouteScreen.CancelOrder.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cancel_order),
                        contentDescription = stringResource(
                            R.string.cancel_order
                        ),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp)
                    )
                    NormalTextComponents(
                        value = stringResource(R.string.cancel_order),
                        nomalColor = Color.Red,
                        nomalFontsize = 20.sp
                    )
                    NormalTextComponents(
                        value = cancelOrder.toString(),
                        nomalColor = Color.Red,
                        nomalFontsize = 18.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.price),
                        contentDescription = stringResource(R.string.revenu_image),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp)
                    )
                    NormalTextComponents(
                        value = "${decimalFormat.format(totalPrice)}đ",
                        nomalColor = Color.Black,
                        nomalFontsize = 20.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            navController.navigate(route = ShopRouteScreen.Charts.route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.analysis),
                        contentDescription = "Thống kê",
                        modifier = Modifier.size(width = 24.dp, height = 24.dp)
                    )
                    NormalTextComponents(
                        value = stringResource(R.string.statistical),
                        nomalColor = Color.Black,
                        nomalFontsize = 20.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardItemAdmin(modifier = Modifier.clickable {
                navController.navigate(route = ShopRouteScreen.AddFood.route)
            })
            CardItemAdmin(
                textCard = R.string.view_all,
                imagecard = R.drawable.visible,
                contentDescription = R.string.visibility_img,
                modifier = Modifier.clickable {
                    navController.navigate(route = HomeRouteScreen.ViewAll.route)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardItemAdmin(
                textCard = R.string.profile,
                imagecard = R.drawable.person,
                contentDescription = R.string.profile,
                modifier = Modifier.clickable {
                    navController.navigate(route = ShopRouteScreen.ProfileAdmin.route)
                }
            )
            CardItemAdmin(
                textCard = R.string.discount_code,
                imagecard = R.drawable.discount,
                contentDescription = R.string.discount_code,
                modifier = Modifier.clickable {
                    navController.navigate(route = ShopRouteScreen.DiscountCode.route)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardItemAdmin(
                textCard = R.string.change_pass_user,
                imagecard = R.drawable.lock,
                contentDescription = R.string.change_pass_user,
                modifier = Modifier.clickable {
                    navController.navigate(route = ShopRouteScreen.ChangePass.route)
                }
            )
            CardItemAdmin(
                textCard = R.string.logout,
                imagecard = R.drawable.logout,
                contentDescription = R.string.logout_icon,
                modifier = Modifier.clickable {
                    openDialog = true
                }
            )
            if (openDialog) {
                AlertDialog(onDismissRequest = { openDialog = false },
                    title = {
                        NormalTextComponents(
                            value = stringResource(R.string.confirm),
                            nomalColor = Color.Black
                        )
                    },
                    text = {
                        Text(text = "Bạn có muốn đăng xuất không?")
                    },
                    confirmButton = {
                        Button(onClick = {
                            openDialog = false
                            homeViewModel.logout()

                            val credentialManager = CredentialManager.create(context)
                            scope.launch {
                                credentialManager.clearCredentialState(
                                    ClearCredentialStateRequest()
                                )
                            }
                            navController.navigate(route = Graph.AUTHGRAPH) {
                                popUpTo(Graph.AUTHGRAPH) {
                                    inclusive = true
                                }
                            }
                        }) {
                            Text(
                                text = stringResource(R.string.confirm),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            openDialog = false
                        }) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    })
            }
        }
    }
}

@Composable
fun CardItemAdmin(
    modifier: Modifier = Modifier,
    colorCard: Color = colorResource(id = R.color.greencard),
    @StringRes textCard: Int = R.string.add_new_food,
    @DrawableRes imagecard: Int = R.drawable.add,
    @StringRes contentDescription: Int = R.string.add_icon
) {
    Card(
        modifier = modifier
            .size(width = 150.dp, height = 100.dp)
            .padding(start = 8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Gray,
                spotColor = Color.DarkGray,
            ), colors = CardDefaults.cardColors(
            containerColor = colorCard, disabledContainerColor = colorCard
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(imagecard), contentDescription = stringResource(
                    id = contentDescription
                ),
                modifier = Modifier.size(width = 24.dp, height = 24.dp)
            )
            NormalTextComponents(
                value = stringResource(id = textCard),
                nomalColor = colorResource(id = R.color.textColor),
                nomalFontsize = 20.sp
            )
        }
    }
}
