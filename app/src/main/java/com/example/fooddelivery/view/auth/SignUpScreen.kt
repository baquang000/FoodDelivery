package com.example.fooddelivery.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.ButtonComponents
import com.example.fooddelivery.components.MyPasswordTextFieldComponents
import com.example.fooddelivery.components.MyTextFieldComponents
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.SignupUIEvent
import com.example.fooddelivery.data.viewmodel.authviewmodel.SignupViewModel
import com.example.fooddelivery.navigation.AuthRouteScreen


@Composable
fun SignUpScreen(
    navController: NavController,
    signupViewModel: SignupViewModel = viewModel()
) {
    var isSuccess by signupViewModel::isSuccess //3.7
    val isError by signupViewModel::isError
    val errormessage by signupViewModel::errormessage //
    var ischeck by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo_AppFood",
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Row(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                NormalTextComponents(
                    value = stringResource(R.string.food_sign_up_login),
                    nomalFontsize = 45.sp,
                    nomalFontWeight = FontWeight.Bold,
                    nomalColor = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )
                NormalTextComponents(
                    value = stringResource(R.string.App_sign_up_login),
                    nomalFontsize = 45.sp,
                    nomalFontWeight = FontWeight.Normal,
                    nomalColor = Color.Black,
                )
            }
            if (isError) {
                NormalTextComponents(
                    value = errormessage.toString(),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Normal,
                    nomalColor = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            MyTextFieldComponents(
                lableText = stringResource(id = R.string.email),
                errorStatus = signupViewModel.signUpUIState.value.emailError
            ) {
                signupViewModel.onEvent(SignupUIEvent.emailChange(it))
            }
            MyPasswordTextFieldComponents(
                lableText = stringResource(id = R.string.Pass_Word),
                errorStatus = signupViewModel.signUpUIState.value.passwordError
            ) {
                signupViewModel.onEvent(SignupUIEvent.PasswordChange(it))
            }
            MyPasswordTextFieldComponents(
                lableText = stringResource(id = R.string.cur_Pass_Word),
                errorStatus = signupViewModel.signUpUIState.value.curpasswordError
            ) {
                signupViewModel.onEvent(SignupUIEvent.CurPasswordChange(it))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(checked = ischeck, onCheckedChange = {
                    ischeck = it
                })
                NormalTextComponents(
                    value = "Tôi đồng ý với ", nomalFontsize = 14.sp,
                    nomalColor = Color.Black
                )
                NormalTextComponents(
                    value = "các điều khoản và điều kiện ", nomalFontsize = 14.sp,
                    nomalColor = Color.Blue,
                    modifier = Modifier.clickable {
                        navController.navigate(route = AuthRouteScreen.Terms.route)
                    }
                )
            }
            ButtonComponents(
                value = stringResource(id = R.string.Dang_Ky),
                isEnable = signupViewModel.allValicationPass.value && ischeck
            ) {
                signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                NormalTextComponents(
                    value = stringResource(R.string.Ban_co_tai_khoan),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Normal,
                    nomalColor = Color.Black,
                    modifier = Modifier.padding(end = 2.dp)
                )
                NormalTextComponents(
                    value = stringResource(R.string.Dang_nhap),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Normal,
                    nomalColor = Color.Red,
                    modifier = Modifier.clickable {
                        navController.navigate(AuthRouteScreen.Login.route)
                    }
                )
            }
        }
        if (signupViewModel.signInProgress.value) {
            CircularProgressIndicator()
        }
        if (isSuccess) {
            AlertDialog(onDismissRequest = { isSuccess = false },
                title = {
                    NormalTextComponents(
                        value = stringResource(R.string.success),
                        nomalColor = Color.Black
                    )
                },
                text = {
                    Text(text = stringResource(R.string.success_signup))
                },
                confirmButton = {
                    Button(onClick = {
                        isSuccess = false
                        navController.navigate(route = AuthRouteScreen.Login.route)
                    }) {
                        Text(text = "Ok")
                    }
                })
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}

