package com.example.freshworkassignment.repo.api

import androidx.lifecycle.MutableLiveData
import com.example.freshworkassignment.common.ErrorEnum
import com.example.freshworkassignment.model.GifModel
import retrofit2.Call
import retrofit2.Response

class MainRepo {

    companion object {
        private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
        private const val API_KEY = "PCn7sbnPQhBBURk1FXlJNVsthCqszPVT"
        private const val LIMIT = 20
        private const val LANGUAGE = "en"

        private val client = ApiClient.getClient(BASE_URL)
        private val apiInterface = client?.create(ApiInterface::class.java)
    }

    var mError: MutableLiveData<ErrorEnum> = MutableLiveData()
    val mSuccess: MutableLiveData<ArrayList<GifModel>> = MutableLiveData()

    fun getTrendingGifs(offset : Int) {
        val trendingGifs = apiInterface?.getTrendingGif(API_KEY, offset, LIMIT)

        trendingGifs?.enqueue(object : retrofit2.Callback<ArrayList<GifModel>> {

            override fun onResponse(call: Call<ArrayList<GifModel>>, response: Response<ArrayList<GifModel>>) {
                if(response.isSuccessful) {
                    mSuccess.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<GifModel>>, t: Throwable) {
                mError.value = ErrorEnum.UNABLE_TO_FETCH_DATA
            }

        })
    }

    fun getSearchResultGif(query : String, offset : Int) {
        val searchResultGifs = apiInterface?.getSearchedGif(API_KEY, query, offset, LIMIT, LANGUAGE)

        searchResultGifs?.enqueue(object : retrofit2.Callback<ArrayList<GifModel>> {
            override fun onResponse(call: Call<ArrayList<GifModel>>, response: Response<ArrayList<GifModel>>) {
                if(response.isSuccessful) {
                    mSuccess.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<GifModel>>, t: Throwable) {
                mError.value = ErrorEnum.UNABLE_TO_FETCH_DATA
            }

        })
    }
}