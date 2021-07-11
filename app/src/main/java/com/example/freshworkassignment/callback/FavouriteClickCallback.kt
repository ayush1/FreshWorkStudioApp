package com.example.freshworkassignment.callback

import com.example.freshworkassignment.eventbus.FavouriteEvent

interface FavouriteClickCallback {
    fun onFavouriteClicked(event : FavouriteEvent)
}