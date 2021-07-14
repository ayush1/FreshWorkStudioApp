package com.example.freshworkassignment.mapper

import com.example.freshworkassignment.db.entity.Favourites
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.model.GifUIModel

class DataMapper {

    /**
     * convert raw gif data to UI data and marking gifs favorite which are present in the database
     * params : rawGifList, favorite marked gifIds
     */
    fun convertRawToUiData(gifList: ArrayList<GifData>, gifIdsFromDb: ArrayList<String>): ArrayList<GifUIModel> {
        val gifUIModelList = ArrayList<GifUIModel>()

        gifList.forEach { gifData ->
            gifUIModelList.add(
                GifUIModel(gifData.gifId, gifData.title, gifData.type, gifData.images.original.gifUrl,
                    gifIdsFromDb.contains(gifData.gifId)))
        }
        return gifUIModelList
    }

    /**
     * convert UI gif data to dao data for insertion in the database
     */
    fun convertUIToDaoData(gifData : GifUIModel) : Favourites {
        return Favourites(gifData.gifId, gifData.type, gifData.title, gifData.gifUrl, gifData.isFavourite)
    }

    /**
     * convert dao data to UI data to show favorite gifs
     */
    fun convertDaoToUIData(list : ArrayList<Favourites>): ArrayList<GifUIModel> {
        val gifUIModelList = ArrayList<GifUIModel>()

        list.forEach { favorite ->
            gifUIModelList.add(
                GifUIModel(favorite.gifId, favorite.title, favorite.type, favorite.gifUrl, favorite.isFavourite))
        }
        return gifUIModelList
    }
}