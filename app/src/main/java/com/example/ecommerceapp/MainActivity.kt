package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.screens.cart.CartScreen
import com.example.ecommerceapp.screens.categories.CategoryScreen
import com.example.ecommerceapp.screens.home.HomeScreen
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.screens.products.ProductDetailsScreen
import com.example.ecommerceapp.screens.products.ProductScreen
import com.example.ecommerceapp.screens.profile.ProfileScreen
import com.example.ecommerceapp.screens.profile.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Navigation System
            val navController = rememberNavController()

            // Nav Host
            NavHost(
                navController = navController,
                startDestination = Screens.Home.route
            ) {

                // define routes using composable(){}
                // for each screen you want to support
                composable(Screens.Home.route) {
                    HomeScreen(
                        navController = navController,
                        onProfileClick = { navController.navigate(Screens.Profile.route) },
                        onCartClick = { navController.navigate(Screens.Cart.route) })
                }
                composable(Screens.Cart.route) {
                    CartScreen(navController = navController)
                }
                composable(Screens.Profile.route) {
                    ProfileScreen(navController = navController, onSignOut = {})
                }
                composable(Screens.CategoryList.route) {
                    CategoryScreen(navController = navController)
                }
                composable(
                    Screens.ProductDetails.route
                ) {
                    val productId = it.arguments?.getString("productId")
                    if (productId != null) {
                        ProductDetailsScreen(productId)
                    }
                }

                composable(Screens.ProductList.route) {
                    val categoryId = it.arguments?.getString("categoryId")
                    if (categoryId != null) {
                        ProductScreen(categoryId, navController = navController)
                    }
                }

                composable(Screens.SignUp.route) {
                    SignUpScreen(
                        navController = navController,
                        onNavigateToLogin = { navController.navigate(Screens.Login.route) },
                        onSignUpSuccess = { navController.navigate(Screens.Home.route) })
                }
                composable(Screens.Login.route) {}
            }
        }
    }
}
