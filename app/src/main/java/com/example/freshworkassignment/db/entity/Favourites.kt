package com.example.freshworkassignment.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.freshworkassignment.common.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class Favourites (

    @PrimaryKey val gifId : String,
    @ColumnInfo(name = "type") val type : String,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "gifurl") val gifUrl : String,
    @ColumnInfo(name = "favaourite") val isFavourite : Boolean)