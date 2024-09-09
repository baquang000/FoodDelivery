package com.example.fooddelivery.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fooddelivery.navigation.AuthRouteScreen
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.view.auth.IntroScreen
import com.example.fooddelivery.view.auth.LoginScreen
import com.example.fooddelivery.view.auth.LoginWithPhoneNumber
import com.example.fooddelivery.view.auth.OtpScreen
import com.example.fooddelivery.view.auth.ResetPasswordScreen
import com.example.fooddelivery.view.auth.SignUpScreen
import com.example.fooddelivery.view.auth.TermsScreen

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
        composable(route = AuthRouteScreen.Terms.route){
            TermsScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.ResetPass.route){
            ResetPasswordScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.LoginPhone.route){
            LoginWithPhoneNumber(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.OtpScreen.route) {
            OtpScreen(navController = rootNavController)
        }
    }
}