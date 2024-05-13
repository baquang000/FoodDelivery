package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.view.home.MainScreen


@Composable
fun RootNavGraph(
) {
    val rootNavController: NavHostController = rememberNavController()
    NavHost(
        navController = rootNavController,
        startDestination = Graph.AUTHGRAPH,
        route = Graph.ROOTGRAPH
    ) {
        authNavGraph(rootNavController = rootNavController)
        composable(route = Graph.HOMEGRAPH){
            MainScreen(rootNavHostController = rootNavController)
        }
    }
}