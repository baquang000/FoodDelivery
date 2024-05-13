package com.example.fooddelivery.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fooddelivery.navigation.AuthRouteScreen
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.view.IntroScreen
import com.example.fooddelivery.view.LoginScreen
import com.example.fooddelivery.view.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    rootNavController: NavHostController
){
    navigation(
        startDestination = AuthRouteScreen.Intro.route,
        route = Graph.AUTHGRAPH
    ) {
        composable(route = AuthRouteScreen.Intro.route) {
            IntroScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.Login.route) {
            LoginScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.SignUp.route) {
            SignUpScreen(navController = rootNavController)
        }
    }
}