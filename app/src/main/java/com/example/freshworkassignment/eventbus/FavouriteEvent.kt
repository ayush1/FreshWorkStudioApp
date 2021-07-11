package com.example.freshworkassignment.eventbus

import com.example.freshworkassignment.common.Constants
import com.example.freshworkassignment.model.GifData

class FavouriteEvent(val gifData: GifData, val position: Int) : Event(Constants.ClickEventName.FAVOURITE_EVENT.name)