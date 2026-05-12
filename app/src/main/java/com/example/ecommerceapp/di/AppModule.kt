package com.example.ecommerceapp.di

import android.content.Context
import com.example.ecommerceapp.repository.CartRepository
import com.example.ecommerceapp.room.AppDatabase
import com.example.ecommerceapp.room.CartDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// This object sets up a hilt module that can provide ROOM & Firebase services
// throughout your app as singleton instances, making DI easy & centralized

@Module // define how to provide certain dep.
@InstallIn(SingletonComponent::class) // dep. are available app-wide
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext
        appContext: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    @Provides
    fun provideCartRepository(cartDao: CartDao):
            CartRepository {
        return CartRepository(cartDao)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
}