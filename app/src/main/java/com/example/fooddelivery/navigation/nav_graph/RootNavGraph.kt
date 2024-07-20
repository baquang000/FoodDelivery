package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.profileviewmodel.OrderFoodViewModel
import com.example.fooddelivery.data.viewmodel.profileviewmodel.UserInforViewModel
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.view.home.MainScreen


@Composable
fun RootNavGraph() {
    val rootNavController: NavHostController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    val userInforViewModel: UserInforViewModel = viewModel()
    val orderViewModel: OrderFoodViewModel = viewModel()
    NavHost(
        navController = rootNavController,
        startDestination = Graph.AUTHGRAPH,
        route = Graph.ROOTGRAPH
    ) {
        authNavGraph(rootNavController = rootNavController)
        composable(route = Graph.HOMEGRAPH) {
            MainScreen(rootNavHostController = rootNavController, sharedViewModel = sharedViewModel,userInforViewModel = userInforViewModel)
        }
        profileNavGraph(rootNavController = rootNavController,
            sharedViewModel = sharedViewModel,
            orderViewModel = orderViewModel,)
        favoriteNavGraph(rootNavController = rootNavController, sharedViewModel = sharedViewModel)
    }
}