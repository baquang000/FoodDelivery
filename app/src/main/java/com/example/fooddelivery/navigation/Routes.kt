package com.example.fooddelivery.navigation

const val SEARCH_ARGUMENT_KEY = "text"
const val TYPE_ARGUMENT_KEY = "type"
const val TITLE_ARGUMENT_KEY = "title"
const val PRICE_ARGUMENT_KEY = "price"
const val STAR_ARGUMENT_KEY = "star"
const val TIMEVALUE_ARGUMENT_KEY = "timevalue"
const val DESCRIPTION_ARGUMENT_KEY = "description"
const val IMAGEPATH_ARGUMENT_KEY = "imagepath"
const val ID_ARGUMENT_KEY = "id"
const val ID_SHOP_ARGUMENT_KEY = "idshop"
const val CATEGORY_ID_KEY = "idshop"

object Graph {
    const val ROOTGRAPH = "rootGraph"
    const val AUTHGRAPH = "authGraph"
    const val HOMEGRAPH = "homeGraph"
    const val SHOPGRAPH = "shopGraph"
    const val FAVORITEGRAPH = "favoriteGraph"
    const val PROFILEGRAPH = "profileGraph"
}

sealed class AuthRouteScreen(val route: String) {
    data object Intro : AuthRouteScreen(route = "Intro_screen")
    data object LoginUser : AuthRouteScreen(route = "LoginUser_screen")
    data object LoginShop : AuthRouteScreen(route = "LoginShop_screen")
    data object SignUpUser : AuthRouteScreen(route = "SignUpUser_screen")
    data object SignUpShop : AuthRouteScreen(route = "SignUpShop_screen")
    data object Terms : AuthRouteScreen(route = "Terms_screen")
    data object ResetPass : AuthRouteScreen(route = "ResetPass_screen")
    data object LoginPhone : AuthRouteScreen(route = "Login_With_Phone_screen")
    data object OtpScreen : AuthRouteScreen(route = "OTP_Screen")
}

sealed class ProfileRouteScreen(val route: String) {
    data object UserInfor : ProfileRouteScreen(route = "UserInfor_screen")
    data object HisFood : ProfileRouteScreen(route = "HisFood_screen")
    data object OrderFood : ProfileRouteScreen(route = "OrderFood_screen")
    data object ChangePass : ProfileRouteScreen(route = "ChangePass_screen")
    data object Comment : ProfileRouteScreen(route = "Comment_screen")
    data object HistoryChatUser : ProfileRouteScreen(route = "HistoryChatUser_screen")

}

sealed class FavoriteRouteScreen(val route: String) {
    data object Favorite : FavoriteRouteScreen(route = "FavoriteRoute_Screen")
}

sealed class HomeRouteScreen(val route: String) {
    data object Home : HomeRouteScreen(route = "Home_Screen")
    data object Category :
        HomeRouteScreen(route = "Category_Screen?categoryId={$CATEGORY_ID_KEY}") {
        fun sendIdCategory(
            categoryId: Int = 0
        ): String {
            return "Category_Screen?categoryId=$categoryId"
        }
    }

    data object Search :
        HomeRouteScreen(route = "Search_Screen?text={$SEARCH_ARGUMENT_KEY}&type={$TYPE_ARGUMENT_KEY}") {
        fun sendText(text: String = "", type: String = ""): String {
            return "Search_Screen?text=$text&type=$type"
        }
    }

    data object ViewAll : HomeRouteScreen(route = "ViewAll_Screen")
    data object FoodDetails :
        HomeRouteScreen(route = "Food_Details_Screen?title={$TITLE_ARGUMENT_KEY}&price={$PRICE_ARGUMENT_KEY}&star={$STAR_ARGUMENT_KEY}&timevalue={$TIMEVALUE_ARGUMENT_KEY}&description={$DESCRIPTION_ARGUMENT_KEY}&imagepath={$IMAGEPATH_ARGUMENT_KEY}&id={$ID_ARGUMENT_KEY}&idshop={$ID_SHOP_ARGUMENT_KEY}") {
        fun sendFood(
            title: String = "",
            price: String = "",
            star: Double = 0.0,
            timevalue: String = "",
            description: String = "",
            imagepath: String = "",
            id: Int = 0,
            idshop: Int = 0
        ): String {
            return "Food_Details_Screen?title=$title&price=$price&star=$star&timevalue=$timevalue&description=$description&imagepath=$imagepath&id=$id&idshop=$idshop"
        }
    }

    data object CartHomeRouteScreen : HomeRouteScreen(route = "Cart_Screen")
    data object ProfileRouteScreen : HomeRouteScreen(route = "Profile_Screen")
    data object ShopRouteScreen :
        HomeRouteScreen(route = "Shop_Screen?idshop={$ID_SHOP_ARGUMENT_KEY}") {
        fun sendIdShop(
            idShop: Int = 0
        ): String {
            return "Shop_Screen?idshop=$idShop"
        }
    }

    data object ChatScreen :
        HomeRouteScreen(route = "Chat_Screen?idShop={$ID_SHOP_ARGUMENT_KEY}&title={$TITLE_ARGUMENT_KEY}") {
        fun sendId(
            idShop: Int = 0,
            title: String = ""
        ): String {
            return "Chat_Screen?idShop=$idShop&title=$title"
        }
    }
}

sealed class ShopRouteScreen(val route: String) {
    data object Home : ShopRouteScreen(route = "Home_Screen")
    data object ViewAll : ShopRouteScreen(route = "ViewAll_Screen")
    data object AddFood : ShopRouteScreen(route = "AddFood_Screen")
    data object ComfirmOrder : ShopRouteScreen(route = "ComfirmOrder_Screen")
    data object DeliveringOrder : ShopRouteScreen(route = "DeliveringOrder_Screen")
    data object DeliveredOrder : ShopRouteScreen(route = "DeliveredOrder_Screen")
    data object CancelOrder : ShopRouteScreen(route = "CancelOrder_Screen")
    data object FoodDetails :
        ShopRouteScreen(route = "Food_Details_Screen?title={$TITLE_ARGUMENT_KEY}&price={$PRICE_ARGUMENT_KEY}&star={$STAR_ARGUMENT_KEY}&timevalue={$TIMEVALUE_ARGUMENT_KEY}&description={$DESCRIPTION_ARGUMENT_KEY}&imagepath={$IMAGEPATH_ARGUMENT_KEY}&id={$ID_ARGUMENT_KEY}") {
        fun sendFood(
            title: String = "",
            price: String = "",
            star: Double = 0.0,
            timevalue: String = "",
            description: String = "",
            imagepath: String = "",
            id: Int = 0
        ): String {
            return "Food_Details_Screen?title=$title&price=$price&star=$star&timevalue=$timevalue&description=$description&imagepath=$imagepath&id=$id"
        }
    }

    data object ProfileAdmin : ShopRouteScreen(route = "Frofile_Screen")

    data object DiscountCode : ShopRouteScreen(route = "DiscountCode_Screen")

    data object DiscountDetail :
        ShopRouteScreen(route = "ShopDiscountDetail_Screen?id={$ID_ARGUMENT_KEY}") {
        fun sendId(
            id: Int = 0
        ): String {
            return "ShopDiscountDetail_Screen?id=$id"
        }
    }

    data object AddDiscountCode : ShopRouteScreen(route = "AddDiscountCode_Screen")

    data object ChangePass : ShopRouteScreen(route = "ChangePass_Screen")

    data object Charts : ShopRouteScreen(route = "Charts_Screen")

    data object ChartsOrder : ShopRouteScreen(route = "Charts_Order_Screen")

    data object ChartsRevenue : ShopRouteScreen(route = "Charts_Revenue_Screen")

    data object ChartsFood : ShopRouteScreen(route = "Charts_Food_Screen")

    data object HistoryChat : ShopRouteScreen(route = "History_Chat_Screen")

    data object ChatByShop :
        HomeRouteScreen(route = "ChatByShop_Screen?idUser={$ID_ARGUMENT_KEY}&title={$TITLE_ARGUMENT_KEY}") {
        fun sendId(
            idUser: Int = 0,
            title: String = ""
        ): String {
            return "ChatByShop_Screen?idUser=$idUser&title=$title"
        }
    }
}


