package com.example.fooddelivery.navigation

const val SEARCH_ARGUMENT_KEY = "text"
const val TITLE_ARGUMENT_KEY = "title"
const val PRICE_ARGUMENT_KEY = "price"
const val STAR_ARGUMENT_KEY = "star"
const val TIMEVALUE_ARGUMENT_KEY = "timevalue"
const val DESCRIPTION_ARGUMENT_KEY = "description"
const val IMAGEPATH_ARGUMENT_KEY = "imagepath"

sealed class Screen(val route: String) {
    data object Intro : Screen(route = "Intro_screen")
    data object Login : Screen(route = "Login_screen")
    data object SignUp : Screen(route = "SignUp_screen")
    data object Home : Screen(route = "Home_Screen")
    data object Pizza : Screen(route = "Pizza_Screen")
    data object Burger : Screen(route = "Burger_Screen")
    data object Chicken : Screen(route = "Chicken_Screen")
    data object HotDog : Screen(route = "HotDog_Screen")
    data object Meat : Screen(route = "Meat_Screen")
    data object Shushi : Screen(route = "Shushi_Screen")
    data object Drink : Screen(route = "Drink_Screen")
    data object More : Screen(route = "More_Screen")
    data object Search : Screen(route = "Search_Screen?text={$SEARCH_ARGUMENT_KEY}") {
        fun sendText(text: String = ""): String {
            return "Search_Screen?text=$text"
        }
    }

    data object ViewAll : Screen(route = "ViewAll_Screen")
    data object FoodDetails :
        Screen(route = "Food_Details_Screen?title={$TITLE_ARGUMENT_KEY}&price={$PRICE_ARGUMENT_KEY}&star={$STAR_ARGUMENT_KEY}&timevalue={$TIMEVALUE_ARGUMENT_KEY}&description={$DESCRIPTION_ARGUMENT_KEY}&imagepath={$IMAGEPATH_ARGUMENT_KEY}") {
        fun sendFood(
            title: String = "",
            price: Double = 0.0,
            star: Double = 0.0,
            timevalue: Int = 0,
            description: String = "",
            imagepath: String = ""
        ):String {
            return "Food_Details_Screen?title=$title&price=$price&star=$star&timevalue=$timevalue&description=$description&imagepath=$imagepath"
        }
    }
    data object CartScreen: Screen(route = "Cart_Screen")
}