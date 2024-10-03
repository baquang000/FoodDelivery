package com.example.fooddelivery.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel.OrderFoodViewModel
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.ProfileRouteScreen
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
            OrderFoodScreen(navController = rootNavController, sharedViewModel = sharedViewModel,orderViewModel = orderViewModel)
        }
        composable(route = ProfileRouteScreen.Comment.route){
            CommentScreen(navController = rootNavController,orderViewModel = orderViewModel)
        }
    }
}