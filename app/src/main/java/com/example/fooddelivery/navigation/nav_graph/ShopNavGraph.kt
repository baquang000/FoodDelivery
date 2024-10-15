package com.example.fooddelivery.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.fooddelivery.navigation.DESCRIPTION_ARGUMENT_KEY
import com.example.fooddelivery.navigation.Graph
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.IMAGEPATH_ARGUMENT_KEY
import com.example.fooddelivery.navigation.PRICE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.STAR_ARGUMENT_KEY
import com.example.fooddelivery.navigation.ShopRouteScreen
import com.example.fooddelivery.navigation.TIMEVALUE_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import com.example.fooddelivery.view.home.profile.ChangePassScreen
import com.example.fooddelivery.view.shop.home.HomeShopScreen
import com.example.fooddelivery.view.shop.home.analysis.AnalysisScreen
import com.example.fooddelivery.view.shop.home.analysis.FoodAnalysisScreen
import com.example.fooddelivery.view.shop.home.analysis.OrderAnalysisScreen
import com.example.fooddelivery.view.shop.home.analysis.RevenueAnalysisScreen
import com.example.fooddelivery.view.shop.home.discount.AddDiscountScreen
import com.example.fooddelivery.view.shop.home.discount.DiscountCodeScreen
import com.example.fooddelivery.view.shop.home.discount.DiscountDetailScreen
import com.example.fooddelivery.view.shop.home.food.AddFoodScreen
import com.example.fooddelivery.view.shop.home.food.FoodDetailsScreen
import com.example.fooddelivery.view.shop.home.food.ViewAllScreen
import com.example.fooddelivery.view.shop.home.order.CancelOrderScreen
import com.example.fooddelivery.view.shop.home.order.DeliveredOrderScreen
import com.example.fooddelivery.view.shop.home.order.DeliveringOrderScreen
import com.example.fooddelivery.view.shop.home.order.PendingOrderScreen
import com.example.fooddelivery.view.shop.home.profile.ProfileAdminScreen

fun NavGraphBuilder.shopNavGraph(
    rootNavController: NavHostController
) {
    navigation(
        route = Graph.SHOPGRAPH,
        startDestination = ShopRouteScreen.Home.route
    ) {
        composable(route = ShopRouteScreen.Home.route) {
            HomeShopScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.ViewAll.route) {
            ViewAllScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.AddFood.route) {
            AddFoodScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.ComfirmOrder.route) {
            PendingOrderScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.DeliveringOrder.route) {
            DeliveringOrderScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.DeliveredOrder.route) {
            DeliveredOrderScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.CancelOrder.route) {
            CancelOrderScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.FoodDetails.route,
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
            FoodDetailsScreen(
                navController = rootNavController,
                navBackStackEntry = navBackStackEntry,
            )
        }
        composable(route = ShopRouteScreen.ProfileAdmin.route) {
            ProfileAdminScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.DiscountCode.route) {
            DiscountCodeScreen(navController = rootNavController)
        }
        composable(route = ShopRouteScreen.ChangePass.route) {
            ChangePassScreen(navController = rootNavController)
        }

        composable(
            route = ShopRouteScreen.DiscountDetail.route,
            arguments = listOf(
                navArgument(ID_ARGUMENT_KEY) {
                    type = NavType.IntType
                },
            )
        ) { navBackStackEntry ->
            DiscountDetailScreen(
                navController = rootNavController,
                navBackStackEntry = navBackStackEntry,
            )
        }

        composable(route = ShopRouteScreen.AddDiscountCode.route) {
            AddDiscountScreen(navController = rootNavController)
        }

        composable(route = ShopRouteScreen.Charts.route){
            AnalysisScreen(navController = rootNavController)
        }

        composable(route = ShopRouteScreen.ChartsOrder.route){
            OrderAnalysisScreen(navController = rootNavController)
        }

        composable(route = ShopRouteScreen.ChartsRevenue.route){
            RevenueAnalysisScreen(navController = rootNavController)
        }

        composable(route = ShopRouteScreen.ChartsFood.route){
            FoodAnalysisScreen(navController = rootNavController)
        }
    }

}