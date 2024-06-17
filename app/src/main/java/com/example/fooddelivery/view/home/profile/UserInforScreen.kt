package com.example.fooddelivery.view.home.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.data.viewmodel.profileviewmodel.UserInforViewModel

@Composable
fun UserInforScreen(
    navController: NavController,
    userInforViewModel: UserInforViewModel = viewModel()
) {
    val name by userInforViewModel::name
    val numberphone by userInforViewModel::numberphone
    val address by userInforViewModel::address
    val isSaving by userInforViewModel::isSaving
    val saveResult by userInforViewModel::saveResult
    val isLoading by userInforViewModel::isLoading
    val loadError by userInforViewModel::loadError

    LaunchedEffect(Unit) {
        userInforViewModel.getUserData()
    }
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    modifier = Modifier.scale(2f)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        userInforViewModel.name = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.name_user))
                    },
                )
                OutlinedTextField(value = numberphone, onValueChange = {
                    userInforViewModel.numberphone = it
                },
                    label = {
                        Text(text = stringResource(id = R.string.SDT))
                    })
                OutlinedTextField(value = address, onValueChange = {
                    userInforViewModel.address = it
                },
                    label = {
                        Text(text = stringResource(id = R.string.address_user))
                    })
                Button(
                    onClick = {
                        userInforViewModel.saveUserData()
                    },
                    modifier = Modifier.width(160.dp)
                ) {
                    Text(
                        text = if (isSaving) stringResource(R.string.is_saving) else stringResource(
                            id = R.string.save
                        ),
                        fontSize = 22.sp
                    )
                }
                saveResult?.let { success ->
                    if (success) {
                        Text(text = stringResource(R.string.save_success))
                    } else {
                        Text(text = stringResource(R.string.save_failed))
                    }
                }
                loadError?.let { error ->
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreUser() {
    UserInforScreen(navController = rememberNavController())
}