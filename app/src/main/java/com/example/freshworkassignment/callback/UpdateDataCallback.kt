package com.example.freshworkassignment.callback

import com.example.freshworkassignment.eventbus.UpdateDataEvent

interface UpdateDataCallback {
    fun onUpdateData(event : UpdateDataEvent)
}