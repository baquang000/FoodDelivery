package com.example.fooddelivery.view.home.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel.ChangePassViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ChangePassScreen(
    navController: NavController,
    changePassViewModel: ChangePassViewModel = viewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var curPass by changePassViewModel::currentPass
    var newPass by changePassViewModel::newPass
    var reNewPass by changePassViewModel::reNewPass
    val isCurPassNull by changePassViewModel::isCurPassNull
    val isNewPassequalRePass by changePassViewModel::isNewPassequalRePass
    val isClickButton by changePassViewModel::isClickButton
    val changePassSuccess by changePassViewModel::changePassSuccess
    val errorMessage by changePassViewModel::errorMessage
    var curPassWordVisibility by remember {
        mutableStateOf(false)
    }
    var newPassWordVisibility by remember {
        mutableStateOf(false)
    }
    var reNewPassWordVisibility by remember {
        mutableStateOf(false)
    }
    val curPassIconVisibility = if (curPassWordVisibility) {
        painterResource(id = R.drawable.visibility)
    } else {
        painterResource(id = R.drawable.unvisible)
    }
    val curPassIconDescription = if (curPassWordVisibility) {
        stringResource(id = R.string.Show_password)
    } else {
        stringResource(id = R.string.Hide_pass_word)
    }
    val newPassIconVisibility = if (newPassWordVisibility) {
        painterResource(id = R.drawable.visibility)
    } else {
        painterResource(id = R.drawable.unvisible)
    }
    val newPassIconDescription = if (newPassWordVisibility) {
        stringResource(id = R.string.Show_password)
    } else {
        stringResource(id = R.string.Hide_pass_word)
    }
    val reNewPassIconVisibility = if (reNewPassWordVisibility) {
        painterResource(id = R.drawable.visibility)
    } else {
        painterResource(id = R.drawable.unvisible)
    }
    val reNewPassIconDescription = if (reNewPassWordVisibility) {
        stringResource(id = R.string.Show_password)
    } else {
        stringResource(id = R.string.Hide_pass_word)
    }
    val localFocusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.change_pass_user),
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 40.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF00FF)
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
            )
        }
    ) { padding ->
        Column(
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
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (isClickButton) {
                if (isCurPassNull) {
                    Text(
                        text = "Mật khẩu hiện tại không được để trống",
                        color = Color.Red,
                        style = TextStyle(
                            fontSize = 12.sp
                        ), modifier = Modifier.padding(start = 32.dp)
                    )
                }
            }
            OutlinedTextField(
                value = curPass,
                onValueChange = {
                    curPass = it
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.padding(top = 2.dp, start = 32.dp, end = 32.dp),
                label = {
                    Text(
                        text = "Nhập mật khẩu hiện tại"
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
                        curPassWordVisibility = !curPassWordVisibility
                    }) {
                        Icon(
                            painter = curPassIconVisibility,
                            contentDescription = curPassIconDescription,
                            modifier = Modifier.width(30.dp)
                        )
                    }
                },
                visualTransformation = if (curPassWordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                ),
            )
            OutlinedTextField(
                value = newPass,
                onValueChange = {
                    newPass = it
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp),
                label = {
                    Text(
                        text = "Nhập mật khẩu mới"
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
                        newPassWordVisibility = !newPassWordVisibility
                    }) {
                        Icon(
                            painter = newPassIconVisibility,
                            contentDescription = newPassIconDescription,
                            modifier = Modifier.width(30.dp)
                        )
                    }
                },
                visualTransformation = if (newPassWordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                ),
            )
            if (isClickButton) {
                if (!isNewPassequalRePass) {
                    Text(
                        text = "Mật khẩu nhập lại phải giống mật khẩu mới",
                        color = Color.Red,
                        style = TextStyle(
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(start = 32.dp, top = 30.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.padding(top = 32.dp))
            }
            OutlinedTextField(
                value = reNewPass,
                onValueChange = {
                    reNewPass = it
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.padding(top = 2.dp, start = 32.dp, end = 32.dp),
                label = {
                    Text(
                        text = "Nhập lại mật khẩu mới"
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
                        reNewPassWordVisibility = !reNewPassWordVisibility
                    }) {
                        Icon(
                            painter = reNewPassIconVisibility,
                            contentDescription = reNewPassIconDescription,
                            modifier = Modifier.width(30.dp)
                        )
                    }
                },
                visualTransformation = if (reNewPassWordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
                ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            changePassViewModel.checkRulesPassWord()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,

                        )
                ) {
                    Text(text = "Thay đổi")
                }
            }
            if (isClickButton) {
                if (changePassSuccess) {
                    Text(
                        "Password changed successfully",
                        color = Color.Green,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                } else {
                    Text(
                        errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            }
        }
    }
}
