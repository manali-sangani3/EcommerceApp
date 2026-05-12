package com.example.ecommerceapp.screens.navigation


sealed class Screens(val route: String) {
    object Home : Screens("Home")
    object Cart : Screens("Cart")
    object Profile : Screens("Profile")

    //    object Categories : Screens("Categories")
    object ProductDetails : Screens("product_details/{productId}") {
        fun createRoute(productId: String) = "product_details/$productId"
    }

    object ProductList : Screens("product_list/{categoryId}") {

        fun createRoute(categoryId: String) =
            "product_list/$categoryId"
    }

    object CategoryList : Screens("category_list")

    object Login : Screens("Login")

    object SignUp : Screens("SignUp")
}