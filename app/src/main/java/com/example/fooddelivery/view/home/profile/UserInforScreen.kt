package com.example.fooddelivery.view.home.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.viewmodel.profileviewmodel.UserInforViewModel

@Composable
fun UserInforScreen(
    navController: NavController,
    userInforViewModel: UserInforViewModel = viewModel()
) {
    val localFocusManager = LocalFocusManager.current
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
            name = it.name
            numberphone = it.numberPhone
            address = it.address
            email = it.email
            dateOfBirth = it.dateOfBirth
        }
    }
    val isLoading by userInforViewModel.isLoadUserInfor.collectAsStateWithLifecycle()
    var isFixDetails by remember {
        mutableStateOf(false)
    }
    var readonlyTextField by remember {
        mutableStateOf(true)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalTextComponents(value = if (!isFixDetails) stringResource(R.string.fix_details)
                else stringResource(id = R.string.cancel),
                    nomalColor = Color.Black, nomalFontsize = 18.sp,
                    modifier = Modifier.clickable {
                        isFixDetails = !isFixDetails
                        readonlyTextField = !readonlyTextField
                    })
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.name_user),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    readOnly = readonlyTextField,
                    textStyle = MaterialTheme.typography.titleMedium,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = numberphone, onValueChange = {
                        numberphone = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.SDT),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    readOnly = readonlyTextField,
                    textStyle = MaterialTheme.typography.titleMedium,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = address, onValueChange = {
                        address = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.address_user),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    readOnly = readonlyTextField,
                    textStyle = MaterialTheme.typography.titleMedium,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = email, onValueChange = {
                        email = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.email),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    readOnly = readonlyTextField,
                    textStyle = MaterialTheme.typography.titleMedium,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = dateOfBirth, onValueChange = {
                        dateOfBirth = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.date_of_birth),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    readOnly = readonlyTextField,
                    textStyle = MaterialTheme.typography.titleMedium,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            localFocusManager.clearFocus()
                        }
                    )
                )
                Button(
                    onClick = {
                        userInforViewModel.saveUserData(
                            name = name,
                            numberPhone = numberphone,
                            address = address,
                            email = email,
                            dateOfBirth = dateOfBirth
                        )
                        isFixDetails = !isFixDetails
                        readonlyTextField = !readonlyTextField
                    },
                    modifier = Modifier.width(160.dp),
                    enabled = !readonlyTextField
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.titleLarge
                    )
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