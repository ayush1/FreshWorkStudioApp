package com.example.freshworkassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freshworkassignment.GifViewHolder
import com.example.freshworkassignment.R
import com.example.freshworkassignment.model.GifData

class GifAdapter(gifListData: ArrayList<GifData>?) : RecyclerView.Adapter<GifViewHolder>() {

    private var gifList: ArrayList<GifData>? = ArrayList()

    init {
        gifList = gifListData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val mInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = mInflater.inflate(R.layout.layout_item_view, parent,false)
        val gifViewHolder = GifViewHolder(view)

        return gifViewHolder
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        gifList?.get(position).let { gifData ->
            holder.bindViewData(gifData)
        }
    }

    override fun getItemCount(): Int {
        return gifList?.size ?: 0
    }

}