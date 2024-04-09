package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fooddelivery.navigation.Screen
import com.example.fooddelivery.view.IntroScreen
import com.example.fooddelivery.view.LoginScreen
import com.example.fooddelivery.view.SignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Intro.route){
        composable(route = Screen.Intro.route){
            IntroScreen(navController = navController)
        }
        composable(route = Screen.Login.route){
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route){
            SignUpScreen(navController = navController)
        }
    }
}