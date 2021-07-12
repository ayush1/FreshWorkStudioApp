package com.example.freshworkassignment.eventbus

import com.example.freshworkassignment.common.Constants
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.model.GifUIModel

class FavouriteEvent(val gifData: GifUIModel, val position: Int) : Event(Constants.ClickEventName.FAVOURITE_EVENT.name)