package com.example.freshworkassignment.repo.api

import com.example.freshworkassignment.model.GifModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("trending")
    fun getTrendingGif(
        @Query("api_key") apiKey: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int) : Call<GifModel>

    @GET("search")
    fun getSearchedGif(
        @Query("api_key") apiKey: String,
        @Query("q") searchQuery: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("lang") languageCode: String) : Call<GifModel>

}