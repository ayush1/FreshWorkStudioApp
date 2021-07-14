package com.example.freshworkassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.freshworkassignment.db.dao.FavouriteDao
import com.example.freshworkassignment.db.entity.Favourites

@Database(entities = [Favourites::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun favouriteDao() : FavouriteDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { appDatabase -> INSTANCE = appDatabase }
            }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "appDatabase.db").build()
    }
}