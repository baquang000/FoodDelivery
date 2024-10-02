package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.FavoriteViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.ShopViewModel
import com.example.fooddelivery.navigation.FavoriteRouteScreen
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.HomeRouteScreen
import com.example.fooddelivery.navigation.ID_SHOP_ARGUMENT_KEY
import com.example.fooddelivery.view.home.favorite.FavoriteScreen
import com.example.fooddelivery.view.home.ShopScreen

fun NavGraphBuilder.favoriteNavGraph(
    homeNavController: NavHostController,
    sharedViewModel: SharedViewModel,
    innerPadding: PaddingValues,
    favoriteViewModel: FavoriteViewModel,
    shopViewModel: ShopViewModel
) {
    navigation(
        startDestination = FavoriteRouteScreen.Favorite.route,
        route = Graph.FAVORITEGRAPH
    ) {
        composable(route = FavoriteRouteScreen.Favorite.route) {
            FavoriteScreen(
                innerPadding = innerPadding,
                navController = homeNavController,
                favoriteViewModel = favoriteViewModel
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