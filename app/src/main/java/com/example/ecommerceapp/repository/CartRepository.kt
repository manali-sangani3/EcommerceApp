package com.example.ecommerceapp.repository

import android.util.Log
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.room.CartDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


// Acts as a bridge between ViewModel and ROOM DB
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    val allCartItems: Flow<List<Product>> =
        cartDao.getAllCartItems()

    suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getCartItemById(product.id)

        if (existingItem != null) {
            Log.v("TAG:::::::", "Product Already Added!")
            cartDao.updateCartItem(product)
        } else {
            cartDao.insertCartItem(product)
            Log.v("TAG:::::::", "Product Added!")
        }
    }

    suspend fun removeFromCart(product: Product) {
        cartDao.deleteCartItem(product)
        Log.v("TAG:::::::", "Product Removed!")
    }

    suspend fun clearCart() {
        cartDao.clearCart()
        Log.v("TAG:::::::", "Cart Cleared!")
    }
}
