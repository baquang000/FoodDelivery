package com.example.fooddelivery.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fooddelivery.navigation.AuthRouteScreen
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.view.auth.IntroScreen
import com.example.fooddelivery.view.auth.shop.LoginShopScreen
import com.example.fooddelivery.view.auth.shop.SignUpShopScreen
import com.example.fooddelivery.view.auth.user.LoginUserScreen
import com.example.fooddelivery.view.auth.user.LoginWithPhoneNumber
import com.example.fooddelivery.view.auth.user.OtpScreen
import com.example.fooddelivery.view.auth.user.ResetPasswordScreen
import com.example.fooddelivery.view.auth.user.SignUpUserScreen
import com.example.fooddelivery.view.auth.TermsScreen

fun NavGraphBuilder.authNavGraph(
    rootNavController: NavHostController
) {
    navigation(
        startDestination = AuthRouteScreen.Intro.route,
        route = Graph.AUTHGRAPH
    ) {
        composable(route = AuthRouteScreen.Intro.route) {
            IntroScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.LoginUser.route) {
            LoginUserScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.SignUpUser.route) {
            SignUpUserScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.LoginShop.route) {
            LoginShopScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.SignUpShop.route) {
            SignUpShopScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.Terms.route) {
            TermsScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.ResetPass.route) {
            ResetPasswordScreen(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.LoginPhone.route) {
            LoginWithPhoneNumber(navController = rootNavController)
        }
        composable(route = AuthRouteScreen.OtpScreen.route) {
            OtpScreen(navController = rootNavController)
        }
    }
}