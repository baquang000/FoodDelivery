package com.example.fooddelivery.navigation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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

const val timeAnimation = 1500

@Composable
fun RootNavGraph() {

    val rootNavController: NavHostController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    val userInforViewModel: UserInforViewModel = viewModel()
    val orderViewModel: OrderFoodViewModel = viewModel()
    NavHost(
        navController = rootNavController,
        startDestination = Graph.AUTHGRAPH,
        route = Graph.ROOTGRAPH,
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
        authNavGraph(rootNavController = rootNavController)
        composable(route = Graph.HOMEGRAPH,
            ) {
            MainScreen(
                rootNavHostController = rootNavController,
                sharedViewModel = sharedViewModel,
                userInforViewModel = userInforViewModel
            )
        }
        profileNavGraph(
            rootNavController = rootNavController,
            sharedViewModel = sharedViewModel,
            orderViewModel = orderViewModel,
        )

    }
}