package com.example.fooddelivery.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel.OrderFoodViewModel
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.HomeRouteScreen
import com.example.fooddelivery.navigation.ID_SHOP_ARGUMENT_KEY
import com.example.fooddelivery.navigation.ProfileRouteScreen
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import com.example.fooddelivery.view.home.chat.ChatScreen
import com.example.fooddelivery.view.home.chat.HistoryChatUserScreen
import com.example.fooddelivery.view.home.profile.ChangePassScreen
import com.example.fooddelivery.view.home.profile.CommentScreen
import com.example.fooddelivery.view.home.profile.HistoryFoodScreen
import com.example.fooddelivery.view.home.profile.OrderFoodScreen
import com.example.fooddelivery.view.home.profile.UserInforScreen

fun NavGraphBuilder.profileNavGraph(
    rootNavController: NavHostController,
    sharedViewModel: SharedViewModel,
    orderViewModel: OrderFoodViewModel,
) {
    navigation(
        startDestination = ProfileRouteScreen.UserInfor.route,
        route = Graph.PROFILEGRAPH
    ) {
        composable(route = ProfileRouteScreen.UserInfor.route) {
            UserInforScreen(navController = rootNavController)
        }
        composable(route = ProfileRouteScreen.HisFood.route) {
            HistoryFoodScreen(navController = rootNavController)
        }
        composable(route = ProfileRouteScreen.ChangePass.route) {
            ChangePassScreen(navController = rootNavController)
        }
        composable(route = ProfileRouteScreen.OrderFood.route) {
            OrderFoodScreen(
                navController = rootNavController,
                sharedViewModel = sharedViewModel,
                orderViewModel = orderViewModel
            )
        }
        composable(route = ProfileRouteScreen.Comment.route) {
            CommentScreen(navController = rootNavController, orderViewModel = orderViewModel)
        }
        composable(route = ProfileRouteScreen.HistoryChatUser.route) {
            HistoryChatUserScreen(navController = rootNavController)
        }
        composable(
            route = HomeRouteScreen.ChatScreen.route,
            arguments = listOf(
                navArgument(ID_SHOP_ARGUMENT_KEY) {
                    type = NavType.IntType
                },
                navArgument(TITLE_ARGUMENT_KEY) {
                    type = NavType.StringType
                }

            )
        ) { navBackStackEntry ->
            ChatScreen(navController = rootNavController, navBackStackEntry = navBackStackEntry)
        }
    }
}