package com.example.fooddelivery.navigation

const val SEARCH_ARGUMENT_KEY = "text"
const val TITLE_ARGUMENT_KEY = "title"
const val PRICE_ARGUMENT_KEY = "price"
const val STAR_ARGUMENT_KEY = "star"
const val TIMEVALUE_ARGUMENT_KEY = "timevalue"
const val DESCRIPTION_ARGUMENT_KEY = "description"
const val IMAGEPATH_ARGUMENT_KEY = "imagepath"
const val ID_ARGUMENT_KEY = "id"
//const val ID_ORDER_ARGUMENT_KEY = "idorder"

sealed class HomeRouteScreen(val route: String) {
    data object Home : HomeRouteScreen(route = "Home_Screen")
    data object Pizza : HomeRouteScreen(route = "Pizza_Screen")
    data object Burger : HomeRouteScreen(route = "Burger_Screen")
    data object Chicken : HomeRouteScreen(route = "Chicken_Screen")
    data object HotDog : HomeRouteScreen(route = "HotDog_Screen")
    data object Meat : HomeRouteScreen(route = "Meat_Screen")
    data object Shushi : HomeRouteScreen(route = "Shushi_Screen")
    data object Drink : HomeRouteScreen(route = "Drink_Screen")
    data object More : HomeRouteScreen(route = "More_Screen")
    data object Search : HomeRouteScreen(route = "Search_Screen?text={$SEARCH_ARGUMENT_KEY}") {
        fun sendText(text: String = ""): String {
            return "Search_Screen?text=$text"
        }
    }

    data object ViewAll : HomeRouteScreen(route = "ViewAll_Screen")
    data object FoodDetails :
        HomeRouteScreen(route = "Food_Details_Screen?title={$TITLE_ARGUMENT_KEY}&price={$PRICE_ARGUMENT_KEY}&star={$STAR_ARGUMENT_KEY}&timevalue={$TIMEVALUE_ARGUMENT_KEY}&description={$DESCRIPTION_ARGUMENT_KEY}&imagepath={$IMAGEPATH_ARGUMENT_KEY}&id={$ID_ARGUMENT_KEY}") {
        fun sendFood(
            title: String = "",
            price: Double = 0.0,
            star: Double = 0.0,
            timevalue: Int = 0,
            description: String = "",
            imagepath: String = "",
            id: Int = 0
        ): String {
            return "Food_Details_Screen?title=$title&price=$price&star=$star&timevalue=$timevalue&description=$description&imagepath=$imagepath&id=$id"
        }
    }

    data object CartHomeRouteScreen : HomeRouteScreen(route = "Cart_Screen")
    data object ProfileRouteScreen : HomeRouteScreen(route = "Profile_Screen")
    data object FavoriteRouteScreen : HomeRouteScreen(route = "FavoriteRoute_Screen")
}

object Graph {
    const val ROOTGRAPH = "rootGraph"
    const val AUTHGRAPH = "authGraph"
    const val HOMEGRAPH = "homeGraph"
    const val FAVORITEGRAPH = "favoriteGraph"
    const val PROFILEGRAPH = "profileGraph"
}

sealed class AuthRouteScreen(val route: String) {
    data object Intro : AuthRouteScreen(route = "Intro_screen")
    data object Login : AuthRouteScreen(route = "Login_screen")
    data object SignUp : AuthRouteScreen(route = "SignUp_screen")
    data object Terms : AuthRouteScreen(route = "Terms_screen")
    data object ResetPass : AuthRouteScreen(route = "ResetPass_screen")
}

sealed class ProfileRouteScreen(val route: String) {
    data object UserInfor : ProfileRouteScreen(route = "UserInfor_screen")
    data object HisFood : ProfileRouteScreen(route = "HisFood_screen")
    data object OrderFood : ProfileRouteScreen(route = "OrderFood_screen")
    data object ChangePass : ProfileRouteScreen(route = "ChangePass_screen")
    data object Comment : ProfileRouteScreen(route = "Comment_screen")
}

sealed class FavoriteRouteScreen(val route: String) {
    data object DetailFavorite :
        FavoriteRouteScreen(route = "Detail_Favorite_Screen?title={$TITLE_ARGUMENT_KEY}&price={$PRICE_ARGUMENT_KEY}&star={$STAR_ARGUMENT_KEY}&timevalue={$TIMEVALUE_ARGUMENT_KEY}&description={$DESCRIPTION_ARGUMENT_KEY}&imagepath={$IMAGEPATH_ARGUMENT_KEY}&id={$ID_ARGUMENT_KEY}") {
        fun sendFood(
            title: String = "",
            price: Double = 0.0,
            star: Double = 0.0,
            timevalue: Int = 0,
            description: String = "",
            imagepath: String = "",
            id: Int = 0
        ): String {
            return "Detail_Favorite_Screen?title=$title&price=$price&star=$star&timevalue=$timevalue&description=$description&imagepath=$imagepath&id=$id"
        }
    }
}