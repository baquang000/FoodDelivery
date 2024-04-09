package com.example.fooddelivery.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.navigation.Screen

@Composable
fun IntroScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .paint(
                    painterResource(id = R.drawable.orange_background),
                    contentScale = ContentScale.FillWidth
                )
                .fillMaxWidth()
                .height(600.dp)
        ) {
            Box(
                modifier = Modifier
                    .paint(
                        painterResource(id = R.drawable.intro_pic),
                        contentScale = ContentScale.FillBounds
                    )
                    .fillMaxWidth()
                    .height(500.dp)
            )
            Box(
                modifier = Modifier
                    .paint(
                        painterResource(id = R.drawable.arc),
                        contentScale = ContentScale.FillWidth
                    )
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF75564)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome To Food App",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Shared and delightful, for an easy and enjoyable food ordering experience",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SignUp",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.SignUp.route)
                        }
                        .padding(start = 24.dp, bottom = 24.dp)
                        ,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Login",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(end = 24.dp, bottom = 24.dp)
                        .clickable {
                            navController.navigate(Screen.Login.route)
                        }
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun IntroScreenPreview() {
    IntroScreen(navController = rememberNavController())
}