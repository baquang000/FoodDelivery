package com.example.fooddelivery.view.auth.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun OtpScreen(navController: NavController) {
    val context = LocalContext.current
    var otp by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, // Remove the grey ripple effect
                interactionSource = MutableInteractionSource()
            ) {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
    ) {
        Text(
            text = "Nhập mã OTP",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        BasicTextField(
            value = otp,
            onValueChange = {
                if (it.length <= 6) {
                    otp = it
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number

            ),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(6) { index ->
                    val number = when {
                        index >= otp.length -> ""
                        else -> otp[index]
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = number.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(2.dp)
                                .background(color = Color.Black),
                        ) {

                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                verifyPhoneNumberWithCod(
                    context = context,
                    verifitionId = storedVerificationId,
                    code = otp,
                    navController = navController
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (otp.length >= 6) Color.Red else Color.Gray,
            ),
            enabled = if (otp.length >= 6) true else false
        ) {
            Text(text = "Xác nhận", fontSize = 15.sp, color = Color.White)
        }
    }
}