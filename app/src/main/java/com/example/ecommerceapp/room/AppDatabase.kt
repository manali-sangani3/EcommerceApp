package com.example.ecommerceapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerceapp.model.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    // Define DAOs here to interact with the DB
    abstract fun cartDao(): CartDao

    //Singleton DB Instance
    companion object{
        @Volatile  // Ensures visibility of changes across threads
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context) : AppDatabase{
            // If INSTANCE is not null --> return it
            // If INSTANCE is null --> execute the code inside
            // the synchronized block to create the DB instance
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cart_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}