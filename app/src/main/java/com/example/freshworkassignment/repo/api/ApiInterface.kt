package com.example.freshworkassignment.repo.api

import com.example.freshworkassignment.model.GifModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        const val LIMIT = 20
        const val LANGUAGE = "en"
        const val API_KEY = "PCn7sbnPQhBBURk1FXlJNVsthCqszPVT"
    }

    @GET("trending")
    fun getTrendingGif(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = LIMIT) : Call<GifModel>

    @GET("search")
    fun getSearchedGif(
        @Query("api_key") apiKey: String,
        @Query("q") searchQuery: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = LIMIT,
        @Query("lang") languageCode: String = LANGUAGE) : Call<GifModel>

}