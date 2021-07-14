package com.example.freshworkassignment.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.freshworkassignment.R
import com.example.freshworkassignment.eventbus.FavouriteEvent
import com.example.freshworkassignment.model.GifUIModel

class GifViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private var mContext : Context = view.context
    private var ivGif : ImageView = view.findViewById(R.id.iv_gif)
    private var ivFavourite : ImageView = view.findViewById(R.id.iv_favorite)

    fun bindViewData(gifData: GifUIModel, position: Int) {
        ivGif.let {
            Glide.with(mContext)
                .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.placeholder_img))
                .load(gifData.gifUrl)
                .into(it)
        }

        if(gifData.isFavourite)
            ivFavourite.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.ic_like))
        else
            ivFavourite.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.ic_unlike))

        ivFavourite.setOnClickListener {
            gifData.isFavourite = !gifData.isFavourite

            val favouriteEvent = FavouriteEvent(gifData, position)
            favouriteEvent.post()
        }
    }
}