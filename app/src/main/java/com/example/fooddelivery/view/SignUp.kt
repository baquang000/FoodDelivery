package com.example.fooddelivery.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.R


@Composable
fun SignUpScreen() {

    var textSDT by remember {
        mutableStateOf("")
    }
    var textPassWord by remember {
        mutableStateOf("")
    }

    val localFocusManager = LocalFocusManager.current

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
            Text(
                text = "Food",
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "App",
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        OutlinedTextField(
            value = textSDT, onValueChange = {
                textSDT = it
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp),
            label = {
                Text(text = "SĐT")
            },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedLabelColor = Color.Red,
                disabledLabelColor = Color.Red,
                focusedLabelColor = Color.Red,
                unfocusedIndicatorColor = Color.Red,
                focusedIndicatorColor = Color.Red,
                unfocusedLeadingIconColor = Color.Red
            ),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone_login")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = textPassWord,
            onValueChange = {
                textPassWord = it
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.padding(top = 32.dp, start = 32.dp, end = 32.dp),
            label = {
                Text(
                    text = "Mật khẩu"
                )
            },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedLabelColor = Color.Red,
                disabledLabelColor = Color.Red,
                focusedLabelColor = Color.Red,
                unfocusedIndicatorColor = Color.Red,
                focusedIndicatorColor = Color.Red,
                disabledTrailingIconColor = Color.Red,
                unfocusedTrailingIconColor = Color.Red,
                focusedTrailingIconColor = Color.Red
            ),
            singleLine = true,
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 32.dp, start = 55.dp, end = 55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            onClick = { /*TODO*/ }) {
            Text(
                text = "Đăng ký",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bạn có tài khoản?",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )
            )
            Text(
                text = "Đăng nhập",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red
                ),
                modifier = Modifier.clickable {

                }
            )
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}