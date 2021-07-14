package com.example.freshworkassignment.application

import android.app.Application
import android.content.Context

class GifApplication : Application() {

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

}