package com.example.freshworkassignment.eventbus

import com.example.freshworkassignment.common.Constants
import com.example.freshworkassignment.model.GifUIModel

class UpdateDataEvent(val gifData: GifUIModel, val position: Int) : Event(Constants.ClickEventName.UPDATE_DATA_EVENT.name)