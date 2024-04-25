package com.example.fooddelivery.navigation

sealed class Screen(val route: String) {
    data object Intro: Screen(route = "Intro_screen")
    data object Login: Screen(route = "Login_screen")
    data object SignUp: Screen(route = "SignUp_screen")
    data object Home: Screen(route = "Home_Screen")
    data object Pizza: Screen(route = "Pizza_Screen")
    data object Burger: Screen(route = "Burger_Screen")
    data object Chicken: Screen(route = "Chicken_Screen")
    data object HotDog: Screen(route = "HotDog_Screen")
    data object Meat: Screen(route = "Meat_Screen")
    data object Shushi: Screen(route = "Shushi_Screen")
    data object Drink: Screen(route = "Drink_Screen")
    data object More: Screen(route = "More_Screen")
}