package com.example.freshworkassignment.mapper

import com.example.freshworkassignment.db.entity.Favourites
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.model.GifUIModel

class MapperRawToUIData {

    fun convertToUiData(gifList: ArrayList<GifData>, gifIdsFromDb: ArrayList<String>): ArrayList<GifUIModel> {
        val gifUIModelList = ArrayList<GifUIModel>()

        gifList.forEach { gifData ->
            gifUIModelList.add(
                GifUIModel(gifData.gifId, gifData.title, gifData.type, gifData.images.original.gifUrl,
                    gifIdsFromDb.contains(gifData.gifId)))
        }
        return gifUIModelList
    }

    fun convertUIToDaoData(gifData : GifUIModel) : Favourites {
        return Favourites(gifData.gifId, gifData.type, gifData.title, gifData.gifUrl, gifData.isFavourite)
    }

    fun convertDaoToUIData(list : ArrayList<Favourites>): ArrayList<GifUIModel> {
        val gifUIModelList = ArrayList<GifUIModel>()

        list.forEach { favoutire ->
            gifUIModelList.add(
                GifUIModel(favoutire.gifId, favoutire.title, favoutire.type, favoutire.gifUrl, favoutire.isFavourite))
        }
        return gifUIModelList
    }
}