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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.navigation.ProfileRouteScreen

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    navController: NavController
) {
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
                        navController.navigate(ProfileRouteScreen.UserInfor.route)
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
                        navController.navigate(route = ProfileRouteScreen.HisFood.route)
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
                    .clickable { },
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
                    .clickable { },
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
        }
    }
}