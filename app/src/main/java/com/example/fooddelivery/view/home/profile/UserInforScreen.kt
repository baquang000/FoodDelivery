package com.example.fooddelivery.view.home.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel.UserInforViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun UserInforScreen(
    navController: NavController,
    userInforViewModel: UserInforViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current
    val userInfor by userInforViewModel.userInfor.collectAsStateWithLifecycle()
    val isLoading by userInforViewModel.isLoadUserInfor.collectAsStateWithLifecycle()
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
    var isFixDetails by remember {
        mutableStateOf(false)
    }
    var readonlyTextField by remember {
        mutableStateOf(true)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.private_info_user),
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 40.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Magenta
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
                },
                actions = {
                    IconButton(onClick = {
                        isFixDetails = !isFixDetails
                        readonlyTextField = !readonlyTextField
                    }) {
                        if (!isFixDetails) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit_info),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .clickable(
                        indication = null, // Remove the grey ripple effect
                        interactionSource = MutableInteractionSource() // Required when setting indication to null
                    ) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {
                    Text(text = stringResource(id = R.string.name_user), fontSize = 18.sp)
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
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
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            disabledTextColor = Color(0xFFA0A4B3),
                            focusedTextColor = Color(0xFFA0A4B3),
                            unfocusedTextColor = Color(0xFFA0A4B3),
                            disabledContainerColor = Color(0xFFBFEFFF),
                            unfocusedContainerColor = Color(0xFFBFEFFF),
                            focusedContainerColor = Color(0xFFBFEFFF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = stringResource(id = R.string.SDT), fontSize = 18.sp)
                    OutlinedTextField(
                        value = numberphone, onValueChange = {
                            numberphone = it
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
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            disabledTextColor = Color(0xFFA0A4B3),
                            focusedTextColor = Color(0xFFA0A4B3),
                            unfocusedTextColor = Color(0xFFA0A4B3),
                            disabledContainerColor = Color(0xFFBFEFFF),
                            unfocusedContainerColor = Color(0xFFBFEFFF),
                            focusedContainerColor = Color(0xFFBFEFFF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = stringResource(id = R.string.address_user), fontSize = 18.sp)
                    OutlinedTextField(
                        value = address, onValueChange = {
                            address = it
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
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            disabledTextColor = Color(0xFFA0A4B3),
                            focusedTextColor = Color(0xFFA0A4B3),
                            unfocusedTextColor = Color(0xFFA0A4B3),
                            disabledContainerColor = Color(0xFFBFEFFF),
                            unfocusedContainerColor = Color(0xFFBFEFFF),
                            focusedContainerColor = Color(0xFFBFEFFF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = stringResource(id = R.string.email), fontSize = 18.sp)
                    OutlinedTextField(
                        value = email, onValueChange = {
                            email = it
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
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            disabledTextColor = Color(0xFFA0A4B3),
                            focusedTextColor = Color(0xFFA0A4B3),
                            unfocusedTextColor = Color(0xFFA0A4B3),
                            disabledContainerColor = Color(0xFFBFEFFF),
                            unfocusedContainerColor = Color(0xFFBFEFFF),
                            focusedContainerColor = Color(0xFFBFEFFF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = stringResource(id = R.string.date_of_birth), fontSize = 18.sp)
                    OutlinedTextField(
                        value = dateOfBirth, onValueChange = {
                            dateOfBirth = it
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
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            disabledTextColor = Color(0xFFA0A4B3),
                            focusedTextColor = Color(0xFFA0A4B3),
                            unfocusedTextColor = Color(0xFFA0A4B3),
                            disabledContainerColor = Color(0xFFBFEFFF),
                            unfocusedContainerColor = Color(0xFFBFEFFF),
                            focusedContainerColor = Color(0xFFBFEFFF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            userInforViewModel.saveUserData(
                                name = name,
                                numberPhone = numberphone,
                                address = address,
                                email = email,
                                dateOfBirth = dateOfBirth,
                                id = ID,
                            )
                        }
                        isFixDetails = !isFixDetails
                        readonlyTextField = !readonlyTextField
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.BottomCenter)
                        .padding(horizontal = 12.dp, vertical = 12.dp),
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