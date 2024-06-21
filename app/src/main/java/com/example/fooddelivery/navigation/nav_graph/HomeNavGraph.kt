package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.HomeRouteScreen
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.SEARCH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import com.example.fooddelivery.view.home.CartScreen
import com.example.fooddelivery.view.home.FavoriteScreen
import com.example.fooddelivery.view.home.FoodDetailsScreen
import com.example.fooddelivery.view.home.HomeScreen
import com.example.fooddelivery.view.home.ProfileScreen
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
fun HomeNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    sharedViewModel : SharedViewModel,
    innerPadding: PaddingValues
) {

    NavHost(
        navController = homeNavController,
        startDestination = HomeRouteScreen.Home.route,
        route = Graph.HOMEGRAPH
    ) {
        composable(route = HomeRouteScreen.Home.route) {
            HomeScreen(
                sharedViewModel = sharedViewModel,
                homeNavController = homeNavController,
                innerPadding = innerPadding
            )
        }
        composable(route = HomeRouteScreen.FavoriteRouteScreen.route) {
            FavoriteScreen(innerPadding = innerPadding, navController = rootNavController)
        }
        composable(route = HomeRouteScreen.ProfileRouteScreen.route) {
            ProfileScreen(innerPadding = innerPadding, navController = rootNavController)
        }
        composable(route = HomeRouteScreen.Pizza.route) {
            PizzaScreen(
                navController = homeNavController,
                sharedViewModel = sharedViewModel,
                innerPaddingValues = innerPadding
            )
        }
        composable(route = HomeRouteScreen.Burger.route) {
            BurgerScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.Shushi.route) {
            ShushiScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.Meat.route) {
            MeatScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.HotDog.route) {
            HotDogScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.Drink.route) {
            DrinkScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.More.route) {
            MoreScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.Chicken.route) {
            ChickenScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.Search.route,
            arguments = listOf(
                navArgument(SEARCH_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            SearchScreen(
                navController = homeNavController,
                backStackEntry = backStackEntry,
                sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding
            )
        }
        composable(route = HomeRouteScreen.ViewAll.route) {
            ViewAllScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
        composable(route = HomeRouteScreen.FoodDetails.route,
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
                },
                navArgument(ID_ARGUMENT_KEY){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            FoodDetailsScreen(
                navController = homeNavController,
                navBackStackEntry = navBackStackEntry,
                sharedViewModel = sharedViewModel,
                innerPaddingValues = innerPadding
            )
        }
        composable(route = HomeRouteScreen.CartHomeRouteScreen.route) {
            CartScreen(navController = homeNavController, sharedViewModel = sharedViewModel,innerPaddingValues = innerPadding)
        }
    }
}
