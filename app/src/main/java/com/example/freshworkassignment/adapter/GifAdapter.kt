package com.example.freshworkassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freshworkassignment.viewholder.GifViewHolder
import com.example.freshworkassignment.R
import com.example.freshworkassignment.model.GifUIModel

class GifAdapter : RecyclerView.Adapter<GifViewHolder>() {

    private var isFavouriteFragment: Boolean = false
    private var gifList: ArrayList<GifUIModel>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val mInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var view = mInflater.inflate(R.layout.layout_item_view, parent,false)

        if(isFavouriteFragment)
            view = mInflater.inflate(R.layout.layout_grid_item_view, parent,false)

        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        gifList?.get(position).let { gifData ->
            if (gifData != null) {
                holder.bindViewData(gifData, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return gifList?.size ?: 0
    }

    /*
    Method helps in using the same adapter for FavoriteFragment as well
     */
    fun setFavouriteAdapter(favourite: Boolean) {
        isFavouriteFragment = favourite
    }

    /*
    Method to update the item when user marked/unmarked the gif favorite
     */
    fun updateItemView(gif : GifUIModel, position: Int) {
        gifList?.set(position, gif)
        notifyItemChanged(position)
    }

    fun setData(gifData : ArrayList<GifUIModel>) {
        gifList = gifData
        notifyDataSetChanged()
    }

}