package com.example.freshworkassignment.model

data class GifUIModel (
    val gifId : String,
    val title : String,
    val type : String,
    val gifUrl : String,
    var isFavourite : Boolean = false)