package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.FavoriteViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.ShopViewModel
import com.example.fooddelivery.navigation.CATEGORY_ID_KEY
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.HomeRouteScreen
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.ID_SHOP_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.SEARCH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TYPE_ARGUMENT_KEY
import com.example.fooddelivery.untils.TokenManager
import com.example.fooddelivery.view.home.CartScreen
import com.example.fooddelivery.view.home.FoodDetailsScreen
import com.example.fooddelivery.view.home.HomeScreen
import com.example.fooddelivery.view.home.ProfileScreen
import com.example.fooddelivery.view.home.SearchScreen
import com.example.fooddelivery.view.home.ShopScreen
import com.example.fooddelivery.view.home.ViewAllScreen
import com.example.fooddelivery.view.home.categoryScreen.CategoryScreen
import java.io.File
import java.io.FileInputStream

@Composable
fun HomeNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    sharedViewModel: SharedViewModel,
    innerPadding: PaddingValues,
) {
    val shopViewModel: ShopViewModel = viewModel()
    val favoriteViewModel: FavoriteViewModel = viewModel()
    val cryptoManager = TokenManager()
    val context = LocalContext.current
    val files = File(context.filesDir, "token.txt")
    val token = cryptoManager.decrypt(inputStream = FileInputStream(files)).decodeToString()
    NavHost(
        navController = homeNavController,
        startDestination = HomeRouteScreen.Home.route,
        route = Graph.HOMEGRAPH,

        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = timeAnimation)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = timeAnimation)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = timeAnimation)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = timeAnimation)
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(durationMillis = timeAnimation)) + slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = timeAnimation)
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(durationMillis = timeAnimation)) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = timeAnimation)
            )
        },
    ) {
        composable(
            route = HomeRouteScreen.Home.route,
        ) {
            HomeScreen(
                sharedViewModel = sharedViewModel,
                homeNavController = homeNavController,
                innerPadding = innerPadding,
            )
        }
        favoriteNavGraph(
            homeNavController = homeNavController,
            sharedViewModel = sharedViewModel,
            shopViewModel = shopViewModel,
            favoriteViewModel = favoriteViewModel,
            innerPadding = innerPadding
        )
        composable(route = HomeRouteScreen.ProfileRouteScreen.route) {
            ProfileScreen(innerPadding = innerPadding, navController = rootNavController)
        }
        composable(route = HomeRouteScreen.Category.route, arguments = listOf(
            navArgument(CATEGORY_ID_KEY) {
                type = NavType.IntType
            }
        )) { backStackEntry ->
            CategoryScreen(
                navController = homeNavController,
                sharedViewModel = sharedViewModel,
                innerPaddingValues = innerPadding,
                backStackEntry = backStackEntry,
            )
        }
        composable(route = HomeRouteScreen.Search.route,
            arguments = listOf(
                navArgument(SEARCH_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(TYPE_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            SearchScreen(
                navController = homeNavController,
                backStackEntry = backStackEntry,
                sharedViewModel = sharedViewModel, innerPaddingValues = innerPadding
            )
        }
        composable(route = HomeRouteScreen.ViewAll.route) {
            ViewAllScreen(
                navController = homeNavController,
                sharedViewModel = sharedViewModel,
                innerPaddingValues = innerPadding,
            )
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
                navArgument(ID_ARGUMENT_KEY) {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            FoodDetailsScreen(
                navController = homeNavController,
                navBackStackEntry = navBackStackEntry,
                sharedViewModel = sharedViewModel,
                shopViewModel = shopViewModel,
                innerPaddingValues = innerPadding
            )
        }
        composable(route = HomeRouteScreen.CartHomeRouteScreen.route) {
            CartScreen(
                navController = homeNavController,
                sharedViewModel = sharedViewModel,
                innerPaddingValues = innerPadding,
            )
        }
        composable(
            route = HomeRouteScreen.ShopRouteScreen.route,
            arguments = listOf(
                navArgument(ID_SHOP_ARGUMENT_KEY) {
                    type = NavType.StringType
                },
            )
        ) { navBackStackEntry ->
            ShopScreen(
                navController = homeNavController, navBackStackEntry = navBackStackEntry,
                innerPaddingValues = innerPadding,
                sharedViewModel = sharedViewModel,
                shopViewModel = shopViewModel,
                favoriteViewModel = favoriteViewModel
            )
        }
    }
}
