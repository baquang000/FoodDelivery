package com.example.fooddelivery.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel.ProfileViewModel
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.ProfileRouteScreen

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .clickable {
                        navController.navigate(ProfileRouteScreen.UserInfor.route){
                            launchSingleTop = true
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(R.string.private_info_user),
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .clickable {
                        navController.navigate(route = ProfileRouteScreen.HisFood.route){
                            launchSingleTop = true
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(R.string.history_user),
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .clickable {
                        navController.navigate(route = ProfileRouteScreen.OrderFood.route){
                            launchSingleTop = true
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.basket),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(R.string.order_screen),
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .clickable {
                        navController.navigate(route = ProfileRouteScreen.ChangePass.route){
                            launchSingleTop = true
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.PunchClock,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(R.string.change_pass_user),
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .clickable {
                        openDialog = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.Output,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(R.string.logout),
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                )
            }
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
                            profileViewModel.logout()
                            navController.navigate(route = Graph.AUTHGRAPH) {
                                popUpTo(Graph.AUTHGRAPH) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }) {
                            Text(text = stringResource(R.string.confirm))
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            openDialog = false
                        }) {
                            Text(text = "Hủy")
                        }
                    })
            }
        }
    }
}