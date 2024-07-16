package com.example.fooddelivery.view.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.data.viewmodel.homeviewmodel.SharedViewModel
import com.example.fooddelivery.data.viewmodel.profileviewmodel.UserInforViewModel
import com.example.fooddelivery.navigation.BottomNavigationBar
import com.example.fooddelivery.navigation.bottomNavigationItemList
import com.example.fooddelivery.navigation.nav_graph.HomeNavGraph


@Composable
fun MainScreen(
    rootNavHostController: NavHostController,
    sharedViewModel: SharedViewModel,
    userInforViewModel: UserInforViewModel,
    homeNavController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val curentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavigationItemList,
                currentRoute = curentRoute
            ) { currentNavigationItem ->
                homeNavController.navigate(currentNavigationItem.route) {
                    homeNavController.graph.startDestinationRoute?.let { startDestinationRoute ->
                        popUpTo(startDestinationRoute) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            }
        }
    ) { innerPadding ->
        HomeNavGraph(
            homeNavController = homeNavController,
            rootNavController = rootNavHostController,
            innerPadding = innerPadding,
            sharedViewModel = sharedViewModel,
            userInforViewModel = userInforViewModel
        )
    }
}