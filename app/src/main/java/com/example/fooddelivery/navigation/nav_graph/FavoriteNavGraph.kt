package com.example.fooddelivery.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.FavoriteRouteScreen
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import com.example.fooddelivery.view.home.favorite.DetailFavoriteScreen

fun NavGraphBuilder.favoriteNavGraph(
    rootNavController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    navigation(
        startDestination = FavoriteRouteScreen.DetailFavorite.route,
        route = Graph.FAVORITEGRAPH
    ) {
        composable(route = FavoriteRouteScreen.DetailFavorite.route,
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
            DetailFavoriteScreen(
                navController = rootNavController,
                navBackStackEntry = navBackStackEntry,
                sharedViewModel = sharedViewModel
            )
        }
    }
}