package com.example.freshworkassignment.repo

import androidx.lifecycle.MutableLiveData
import com.example.freshworkassignment.common.Constants
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.model.GifModel
import com.example.freshworkassignment.repo.api.ApiClient
import com.example.freshworkassignment.repo.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
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

    var mError: MutableLiveData<Constants.ErrorEnum> = MutableLiveData()
    val mSuccess: MutableLiveData<ArrayList<GifData>> = MutableLiveData()

    /*
    Method to get the trending gif results
     */
    fun getTrendingGifs(offset : Int) {
        val trendingGifs = apiInterface?.getTrendingGif(API_KEY, offset, LIMIT)

        trendingGifs?.enqueue(object : Callback<GifModel> {
            override fun onResponse(call: Call<GifModel>, response: Response<GifModel>) {
                if(response.isSuccessful) {
                    mSuccess.value = response.body()?.gifData
                }
            }

            override fun onFailure(call: Call<GifModel>, t: Throwable) {
                mError.value = Constants.ErrorEnum.UNABLE_TO_FETCH_DATA
            }
        })
    }

    /*
    Method to get the searched query gif results
     */
    fun getSearchResultGif(query : String, offset : Int) {
        val searchResultGifs = apiInterface?.getSearchedGif(API_KEY, query, offset, LIMIT, LANGUAGE)

        searchResultGifs?.enqueue(object : Callback<GifModel> {
            override fun onResponse(call: Call<GifModel>, response: Response<GifModel>) {
                if(response.isSuccessful) {
                    mSuccess.value = response.body()?.gifData
                }
            }

            override fun onFailure(call: Call<GifModel>, t: Throwable) {
                mError.value = Constants.ErrorEnum.UNABLE_TO_FETCH_DATA
            }

        })
    }
}