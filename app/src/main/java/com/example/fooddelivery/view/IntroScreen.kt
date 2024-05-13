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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.navigation.AuthRouteScreen

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
            NormalTextComponents(
                value = stringResource(id = R.string.Welcome_To_Food_App),
                nomalFontsize = 26.sp,
                nomalFontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            NormalTextComponents(
                value = stringResource(id = R.string.Shared_and_delightful_for_an_easy_and_enjoyable_food_ordering_experience),
                modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NormalTextComponents(value = stringResource(id = R.string.Sign_Up),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Bold,
                    nomalTextAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 24.dp, bottom = 24.dp)
                        .clickable {
                            navController.navigate(AuthRouteScreen.SignUp.route)
                        })
                NormalTextComponents(value = stringResource(id = R.string.Login),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Bold,
                    nomalTextAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(end = 24.dp, bottom = 24.dp)
                        .clickable {
                            navController.navigate(AuthRouteScreen.Login.route)
                        })
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