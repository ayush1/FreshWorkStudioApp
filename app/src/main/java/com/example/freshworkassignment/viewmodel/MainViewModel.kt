package com.example.freshworkassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.model.GifModel
import com.example.freshworkassignment.repo.MainRepo
import java.util.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepo : MainRepo = MainRepo()
    var mUIError = MutableLiveData<String>()
    var mUIResponse = MutableLiveData<ArrayList<GifData>>()
    var favouriteGifList : ArrayList<GifData> = ArrayList()
    var favouriteLiveData = MutableLiveData<ArrayList<GifData>>()

    init {
        mRepo.mSuccess.observeForever { data ->
            mUIResponse.value = data
        }

        mRepo.mError.observeForever { error ->
            mUIError.value = error.msg
        }
    }

    fun getTrendingGifData(offset : Int) {
        mRepo.getTrendingGifs(offset)
    }

    fun getSearchGifData(queryString : String, offset : Int) {
        mRepo.getSearchResultGif(queryString, offset)
    }

    fun addOrRemoveFavourite(gif: GifData) {
        if(gif.isFavourite)
            favouriteGifList.add(gif)
        else
            favouriteGifList.remove(gif)

        favouriteLiveData.value = favouriteGifList
    }
}