package com.example.freshworkassignment.util

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkRequest
import com.example.freshworkassignment.application.GifApplication

class Utils {

    companion object {

        /**
         * Register for Network callback : handles network state
         */
        fun registerNetworkAvailability(networkCallback: NetworkCallback) {
            val connectivityManager = GifApplication.appContext?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()

            connectivityManager.registerNetworkCallback(builder.build(),networkCallback)
        }

        /**
         * check for internet connection
         */
        fun isNetworkAvailable(): Boolean {
            val connectivityManager = GifApplication.appContext?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
            val networks = connectivityManager?.allNetworks

            return networks?.size != 0
        }
    }

}