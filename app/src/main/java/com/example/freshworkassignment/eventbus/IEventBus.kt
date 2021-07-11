package com.example.freshworkassignment.eventbus

interface IEventBus {
    fun <T : Event?> postEvent(event: T)

    fun register(subscriber: Any?)

    fun unregister(subscriber: Any?)
}