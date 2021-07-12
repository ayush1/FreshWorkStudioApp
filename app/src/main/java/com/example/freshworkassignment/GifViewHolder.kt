package com.example.freshworkassignment

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.freshworkassignment.eventbus.FavouriteEvent
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

    fun bindViewData(gifData: GifData, position: Int) {
        val requestOption = RequestOptions()
        requestOption.placeholder(R.drawable.placeholder_img)

        ivGif.let {
            Glide.with(mContext)
                .setDefaultRequestOptions(requestOption)
                .load(gifData.images.original.gifUrl)
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