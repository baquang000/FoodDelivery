package com.example.fooddelivery.navigation.nav_graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fooddelivery.data.viewmodel.SharedViewModel
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.SEARCH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.Screen
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import com.example.fooddelivery.view.IntroScreen
import com.example.fooddelivery.view.LoginScreen
import com.example.fooddelivery.view.SignUpScreen
import com.example.fooddelivery.view.home.CartScreen
import com.example.fooddelivery.view.home.FoodDetailsScreen
import com.example.fooddelivery.view.home.HomeScreen
import com.example.fooddelivery.view.home.SearchScreen
import com.example.fooddelivery.view.home.ViewAllScreen
import com.example.fooddelivery.view.home.categoryScreen.BurgerScreen
import com.example.fooddelivery.view.home.categoryScreen.ChickenScreen
import com.example.fooddelivery.view.home.categoryScreen.DrinkScreen
import com.example.fooddelivery.view.home.categoryScreen.HotDogScreen
import com.example.fooddelivery.view.home.categoryScreen.MeatScreen
import com.example.fooddelivery.view.home.categoryScreen.MoreScreen
import com.example.fooddelivery.view.home.categoryScreen.PizzaScreen
import com.example.fooddelivery.view.home.categoryScreen.ShushiScreen

@Composable
fun SetupNavGraph(
) {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
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
            HomeScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Pizza.route) {
            PizzaScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Burger.route) {
            BurgerScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Shushi.route) {
            ShushiScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Meat.route) {
            MeatScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.HotDog.route) {
            HotDogScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Drink.route) {
            DrinkScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.More.route) {
            MoreScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Chicken.route) {
            ChickenScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Search.route,
            arguments = listOf(
                navArgument(SEARCH_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            SearchScreen(
                navController = navController,
                backStackEntry = backStackEntry,
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = Screen.ViewAll.route) {
            ViewAllScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.FoodDetails.route,
            arguments = listOf(
                navArgument(TITLE_ARGUMENT_KEY) {
                    type = NavType.StringType
                },
                navArgument(PRICE_ARGUMENT_KEY) {
                    type = NavType.FloatType
                },
                navArgument(STAR_ARGUMENT_KEY) {
                    type = NavType.FloatType
                },
                navArgument(TIMEVALUE_ARGUMENT_KEY) {
                    type = NavType.IntType
                },
                navArgument(DESCRIPTION_ARGUMENT_KEY) {
                    type = NavType.StringType
                },
                navArgument(IMAGEPATH_ARGUMENT_KEY) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val imagepath = navBackStackEntry.arguments?.getString(IMAGEPATH_ARGUMENT_KEY)
            Log.e("image", "$imagepath")
            FoodDetailsScreen(
                navController = navController,
                navBackStackEntry = navBackStackEntry,
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = Screen.CartScreen.route) {
            CartScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
    }

}