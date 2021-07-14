package com.example.freshworkassignment.eventbus

import android.util.Log

abstract class Event protected constructor(var name: String?) {

    var isConsumed: Boolean=false

    fun post() {
        Log.d("Event", "Fired Event : $this")
        EventBusClass.postEvent(this)
    }
}