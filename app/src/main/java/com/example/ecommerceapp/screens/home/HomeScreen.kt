package com.example.ecommerceapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.viewmodels.CategoryViewModel
import com.example.ecommerceapp.viewmodels.ProductViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    onProfileClick: () -> Unit,
    onCartClick: () -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            MyTopAppBar(
                onProfileClick = onProfileClick,
                onCartClick = onCartClick
            )
        },
        bottomBar = { BottomNavBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Section
            val searchQuery = remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onSearchFocusChange = {},
                onSearch = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Categories Section
            SectionTitle("Categories", "See All") {
                navController.navigate(Screens.CategoryList.route)
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Mock the categories
            val categoriesState = categoryViewModel.categories.collectAsState()
            val categories = categoriesState.value

            // The Selected Category
            val selectedCategory = remember { mutableStateOf(0) }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) {
                    CategoryChip(
                        icon = categories[it].imageUrl,
                        text = categories[it].name,
                        isSelected = selectedCategory.value == categories[it].id,
                        onClick =
                            {
                                selectedCategory.value = categories[it].id
                                navController.navigate(
                                    Screens.ProductList.createRoute(
                                        selectedCategory.value.toString()
                                    )
                                )

                            }
                    )
                }
            }

            // Featured Products Section
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Featured", "See All") {

                navController.navigate(Screens.CategoryList.route)
            }

            Spacer(modifier = Modifier.height(8.dp))
            // Fetch products when the screen IS TIPSL displayed
            productViewModel.getAllProductsInFirestore()
            val allProductsState = productViewModel.allProducts.collectAsState()

            val allproductsFound = allProductsState.value

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allproductsFound) { product ->
                    FeatureProductCard(product) {
                        navController.navigate(
                            Screens.ProductDetails.createRoute(product.id)
                        )
                    }
                }
            }
        }

    }
}