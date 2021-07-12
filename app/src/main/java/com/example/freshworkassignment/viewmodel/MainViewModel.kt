package com.example.freshworkassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.freshworkassignment.db.AppDatabase
import com.example.freshworkassignment.db.dao.FavouriteDao
import com.example.freshworkassignment.db.entity.Favourites
import com.example.freshworkassignment.mapper.MapperRawToUIData
import com.example.freshworkassignment.model.GifUIModel
import com.example.freshworkassignment.repo.MainRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepo : MainRepo = MainRepo()
    private var favouriteDao : FavouriteDao? = null
    private var mapperRawToUIData : MapperRawToUIData = MapperRawToUIData()
    var mUIError = MutableLiveData<String>()
    var mUIResponse = MutableLiveData<ArrayList<GifUIModel>>()
    var favouriteLiveData = MutableLiveData<ArrayList<GifUIModel>>()

    init {
        val database = AppDatabase.getDatabase(application)
        favouriteDao = database.favouriteDao()

        mRepo.mSuccess.observeForever { data ->

            mUIResponse.value = mapperRawToUIData.convertToUiData(data, getGifIdsFromDb())
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

    fun addOrRemoveFavourite(gif: GifUIModel) {
        if(gif.isFavourite)
            insertFavouriteGifInDb(gif)
        else
            deleteFavouriteGifInDb(gif)

        favouriteLiveData.value = getFavouriteGifFromDb()
    }

    private fun insertFavouriteGifInDb(daoData: GifUIModel) {
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                favouriteDao?.insert(mapperRawToUIData.convertUIToDaoData(daoData))
            }
            return@runBlocking
        }
    }

    private fun deleteFavouriteGifInDb(daoData: GifUIModel) {
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                favouriteDao?.delete(mapperRawToUIData.convertUIToDaoData(daoData))
            }
            return@runBlocking
        }
    }

    fun getFavouriteGifFromDb() : ArrayList<GifUIModel> = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            mapperRawToUIData.convertDaoToUIData(favouriteDao?.getAll() as ArrayList<Favourites>)
        }
    }

    fun getGifIdsFromDb() : ArrayList<String> = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            favouriteDao?.getAllGifIds() as ArrayList<String>
        }
    }
}