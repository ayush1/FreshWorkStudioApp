package com.example.freshworkassignment.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.freshworkassignment.common.Constants
import com.example.freshworkassignment.db.AppDatabase
import com.example.freshworkassignment.db.dao.FavouriteDao
import com.example.freshworkassignment.db.entity.Favourites
import com.example.freshworkassignment.mapper.DataMapper
import com.example.freshworkassignment.model.GifUIModel
import com.example.freshworkassignment.repo.MainRepo
import com.example.freshworkassignment.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepo : MainRepo = MainRepo()
    private var favouriteDao : FavouriteDao? = null
    private var dataMapper : DataMapper = DataMapper()
    var mUIError = MutableLiveData<String>()
    var mUIResponse = MutableLiveData<ArrayList<GifUIModel>>()
    var favoriteLiveData = MutableLiveData<ArrayList<GifUIModel>>()
    var emptyFavoriteLiveData = MutableLiveData<String>()
    var offset : Int = 0
    var searchQuery : String = ""

    init {
        val database = AppDatabase.getDatabase(application)
        favouriteDao = database.favouriteDao()

        mRepo.mSuccess.observeForever { data ->
            ++offset
            mUIResponse.value = dataMapper.convertRawToUiData(data, getGifIdsFromDb())
        }

        mRepo.mError.observeForever { error ->
            mUIError.value = error.msg
        }

        registerNetworkStateChange()
    }

    /*
    Method Look for the Network State changes: take appropriate action accordingly
     */
    private fun registerNetworkStateChange() {
        Utils.registerNetworkAvailability(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                mRepo.getTrendingGifs(offset)
            }

            override fun onUnavailable() {
                mUIError.value = Constants.ErrorEnum.INTERNET_CONNECTION.msg
            }
        })
    }

    /*
    Method get trending gifs : from the API
     */
    fun getTrendingGif() {
        if(Utils.isNetworkAvailable())
            mRepo.getTrendingGifs(offset)
        else
            mUIError.value = Constants.ErrorEnum.INTERNET_CONNECTION.msg
    }

    /*
    Method get gifs : on the basis of searched query
     */
    fun getSearchGif() {
        mRepo.getSearchResultGif(searchQuery, offset)
    }

    /*
    database query : add/remove favorite gif in room database
     */
    fun addOrRemoveFavourite(gif: GifUIModel) {
        if(gif.isFavourite)
            insertFavouriteGifInDb(gif)
        else
            deleteFavouriteGifInDb(gif)

        getFavouriteGifFromDb()
    }

    /*
    Insert query : favorite gif into database
     */
    private fun insertFavouriteGifInDb(daoData: GifUIModel) {
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                favouriteDao?.insert(dataMapper.convertUIToDaoData(daoData))
            }
            return@runBlocking
        }
    }

    /*
    Delete query : remove favorite gif from database
     */
    private fun deleteFavouriteGifInDb(daoData: GifUIModel) {
        runBlocking(Dispatchers.Default){
            withContext(Dispatchers.Default) {
                favouriteDao?.delete(dataMapper.convertUIToDaoData(daoData))
            }
            return@runBlocking
        }
    }

    /*
    Select query : get all the favorite gifs from database
     */
    fun getFavouriteGifFromDb() = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            val list = favouriteDao?.getAll() as ArrayList<Favourites>
            if (list.size > 0)
                favoriteLiveData.postValue(dataMapper.convertDaoToUIData(list))
            else
                emptyFavoriteLiveData.postValue(Constants.EmptyEnum.NO_FAVORITE_DATA_FOUND.msg)
        }
    }

    /*
    Select query : get all the favorite gif ids to show the favorite gifs on trending/search screen
     */
    private fun getGifIdsFromDb() : ArrayList<String> = runBlocking(Dispatchers.Default) {
        return@runBlocking withContext(Dispatchers.Default) {
            favouriteDao?.getAllGifIds() as ArrayList<String>
        }
    }

    /*
    get item index which is removed from the favorite list
     */
    fun getItemIndex(gifData: GifUIModel) : Int{
        mUIResponse.value?.forEachIndexed { index, gifUIModel ->
            if(gifData.gifId.equals(gifUIModel.gifId, true))
                return index
        }
        return -1
    }
}