package com.example.fooddelivery.navigation

sealed class Screen(val route: String) {
    data object Intro: Screen(route = "Intro_screen")
    data object Login: Screen(route = "Login_screen")
    data object SignUp: Screen(route = "SignUp_screen")
}