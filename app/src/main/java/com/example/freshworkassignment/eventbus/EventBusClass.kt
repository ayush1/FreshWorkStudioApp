package com.example.freshworkassignment.eventbus

import org.greenrobot.eventbus.EventBus

object EventBusClass : IEventBus {

    private val mEventBus: EventBus

    init {
        mEventBus = EventBus.getDefault()
    }

    override fun <T : Event?> postEvent(event: T) {
        mEventBus.post(event)
    }

    override fun register(subscriber: Any?) {
        if (!mEventBus.isRegistered(subscriber)) {
            mEventBus.register(subscriber)
        }
    }

    override fun unregister(subscriber: Any?) {
        if (mEventBus.isRegistered(subscriber)) {
            mEventBus.unregister(subscriber)
        }
    }
}