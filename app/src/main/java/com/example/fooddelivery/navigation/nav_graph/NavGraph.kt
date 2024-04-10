package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.navigation.Screen
import com.example.fooddelivery.view.HomeScreen
import com.example.fooddelivery.view.IntroScreen
import com.example.fooddelivery.view.LoginScreen
import com.example.fooddelivery.view.SignUpScreen

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Intro.route) {
        composable(route = Screen.Intro.route) {
            IntroScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.Home.route){
            HomeScreen()
        }
    }
}