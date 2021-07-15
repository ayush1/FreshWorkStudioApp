package com.example.freshworkassignment

import com.example.freshworkassignment.model.GifUIModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewModelUnitTest {

    private var gifUIModelList : ArrayList<GifUIModel> = ArrayList()

    @Test
    fun check_correct_gif_index() {
        initGifUIModelList()
        val gifUiModel = GifUIModel("pqr123def", "gif", "Testing Gif_3",
            "https://media1.giphy.com/media/ZYENqjb4a515zYWgNS/200.gif?cid=75a5bf084dqv0uj0k808c1ir33hknwdtkl7v9m45w08kglpc&rid=200.gif&ct=g",
            false)
        var index = -1

        gifUIModelList.forEachIndexed { i, model ->
            if(model.equals(gifUiModel))
                index = i
        }

        assertEquals(index, 2)
    }

    private fun initGifUIModelList() {
        gifUIModelList.add(GifUIModel("abc123def", "gif", "Testing Gif_1",
            "https://media1.giphy.com/media/ZYENqjb4a515zYWgNS/200.gif?cid=75a5bf084dqv0uj0k808c1ir33hknwdtkl7v9m45w08kglpa&rid=200.gif&ct=g",
            true))

        gifUIModelList.add(GifUIModel("xyz123abc", "gif", "Testing Gif_2",
            "https://media1.giphy.com/media/ZYENqjb4a515zYWgNS/200.gif?cid=75a5bf084dqv0uj0k808c1ir33hknwdtkl7v9m45w08kglpb&rid=200.gif&ct=g",
            false))

        gifUIModelList.add(GifUIModel("pqr123def", "gif", "Testing Gif_3",
            "https://media1.giphy.com/media/ZYENqjb4a515zYWgNS/200.gif?cid=75a5bf084dqv0uj0k808c1ir33hknwdtkl7v9m45w08kglpc&rid=200.gif&ct=g",
            false))
    }
}