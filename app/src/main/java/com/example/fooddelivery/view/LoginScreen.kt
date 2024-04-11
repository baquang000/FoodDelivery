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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.ButtonComponents
import com.example.fooddelivery.components.DrawLineAndTextComponents
import com.example.fooddelivery.components.MyPasswordTextFieldComponents
import com.example.fooddelivery.components.MyTextFieldComponents
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController
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
        MyTextFieldComponents(lableText = stringResource(id = R.string.SDT)){

        }
        MyPasswordTextFieldComponents(lableText = stringResource(id = R.string.Pass_Word)){

        }
        NormalTextComponents(
            value = stringResource(R.string.ban_quen_mat_khau),
            nomalFontsize = 16.sp,
            nomalFontWeight = FontWeight.Normal,
            nomalColor = Color.Black,
            modifier = Modifier.padding(top = 32.dp)
        )
        ButtonComponents(value = stringResource(id = R.string.Dang_nhap)){

        }

        DrawLineAndTextComponents()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.Center
        )
        {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "logo_facebook",
                modifier = Modifier
                    .clickable {

                    }
                    .weight(1f)
                    .height(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "logo_google",
                modifier = Modifier
                    .clickable {

                    }
                    .weight(1f)
                    .height(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "logo_twitter",
                modifier = Modifier
                    .clickable {

                    }
                    .weight(1f)
                    .height(50.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            NormalTextComponents(
                value = stringResource(R.string.ban_khong_co_tai_khoan),
                nomalFontsize = 16.sp,
                nomalFontWeight = FontWeight.Normal,
                nomalColor = Color.Black,
                modifier = Modifier.padding(end = 2.dp)
            )
            NormalTextComponents(
                value = stringResource(id = R.string.Dang_Ky),
                nomalFontsize = 16.sp,
                nomalFontWeight = FontWeight.Normal,
                nomalColor = Color.Red,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}