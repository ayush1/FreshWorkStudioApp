package com.example.freshworkassignment.repo.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap

class ApiClient {

    companion object {
        private val mUrlToClientMap = HashMap<String, Retrofit>()

        fun getClient(rootPath: String): Retrofit? {
            var clientToReturn: Retrofit? = mUrlToClientMap[rootPath]
            if (clientToReturn == null) {
                val okHttpClient = OkHttpClient.Builder().build()
                val client = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(rootPath)
                    .build()
                clientToReturn = client
                mUrlToClientMap[rootPath] = client
            }

            return clientToReturn

        }
    }
}