package com.example.freshworkassignment.db.dao

import androidx.room.*
import com.example.freshworkassignment.common.Constants.Companion.TABLE_NAME
import com.example.freshworkassignment.db.entity.Favourites

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll() : List<Favourites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gif : Favourites)

    @Delete
    fun delete(gif: Favourites)

    @Query("SELECT gifId FROM $TABLE_NAME")
    fun getAllGifIds() : List<String>
}