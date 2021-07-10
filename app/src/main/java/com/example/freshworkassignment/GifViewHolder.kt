package com.example.freshworkassignment

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freshworkassignment.model.GifData

class GifViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private var mContext : Context
    private var ivGif : ImageView
    private var ivFavourite : ImageView

    init {
        mContext = view.context
        ivGif = view.findViewById(R.id.iv_gif)
        ivFavourite = view.findViewById(R.id.iv_favourite)
    }

    fun bindViewData(gifData: GifData?) {
        ivGif.let {
            Glide.with(mContext)
                .load(gifData?.images?.original?.gifUrl)
                .into(it)
        }

        ivFavourite.setOnClickListener {
            //TODO Add it to Favourite
        }
    }
}