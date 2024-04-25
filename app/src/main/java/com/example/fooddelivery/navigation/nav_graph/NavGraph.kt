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
import com.example.fooddelivery.view.categoryScreen.BurgerScreen
import com.example.fooddelivery.view.categoryScreen.ChickenScreen
import com.example.fooddelivery.view.categoryScreen.DrinkScreen
import com.example.fooddelivery.view.categoryScreen.HotDogScreen
import com.example.fooddelivery.view.categoryScreen.MeatScreen
import com.example.fooddelivery.view.categoryScreen.MoreScreen
import com.example.fooddelivery.view.categoryScreen.PizzaScreen
import com.example.fooddelivery.view.categoryScreen.ShushiScreen

@Composable
fun SetupNavGraph(
) {
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
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Pizza.route) {
            PizzaScreen(navController = navController)
        }
        composable(route = Screen.Burger.route) {
            BurgerScreen(navController = navController)
        }
        composable(route = Screen.Shushi.route) {
            ShushiScreen(navController = navController)
        }
        composable(route = Screen.Meat.route) {
            MeatScreen(navController = navController)
        }
        composable(route = Screen.HotDog.route) {
            HotDogScreen(navController = navController)
        }
        composable(route = Screen.Drink.route) {
            DrinkScreen(navController = navController)
        }
        composable(route = Screen.More.route) {
            MoreScreen(navController = navController)
        }
        composable(route = Screen.Chicken.route) {
            ChickenScreen(navController = navController)
        }
    }

}