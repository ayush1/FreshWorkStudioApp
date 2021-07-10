package com.example.freshworkassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.freshworkassignment.model.GifModel
import com.example.freshworkassignment.repo.api.MainRepo
import java.util.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepo : MainRepo
    var mUIError = MutableLiveData<String>()
    var mUIResponse = MutableLiveData<ArrayList<GifModel>>()

    init {
        mRepo = MainRepo()

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
}