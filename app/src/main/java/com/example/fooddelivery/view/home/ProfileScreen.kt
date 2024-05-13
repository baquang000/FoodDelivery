package com.example.fooddelivery.view.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    navController: NavController
){
    Surface {
        Text(text = "Profile")
    }
}