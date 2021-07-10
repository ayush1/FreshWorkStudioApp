package com.example.freshworkassignment.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshworkassignment.R
import com.example.freshworkassignment.adapter.GifAdapter
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.layout_trending_fragment.*

class TrendingGifFragment : Fragment() {

    private var trendingGifViewModel: MainViewModel? = null
    private var mView : View? = null
    private var mContext : Context? = null
    private var mGifListData : ArrayList<GifData>? = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.layout_trending_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trendingGifViewModel?.getTrendingGifData(0)

        trendingGifViewModel?.mUIResponse?.observe(viewLifecycleOwner, object : Observer<ArrayList<GifData>> {
            override fun onChanged(gifList: ArrayList<GifData>?) {
                mGifListData = gifList
                populateGif()
            }
        })

        trendingGifViewModel?.mUIError?.observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(error: String) {
                populateError(error)
            }
        })
    }

    private fun populateError(error: String) {
        Log.d("Fragment_response", error)
    }

    private fun initViewModel() {
        trendingGifViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    private fun populateGif() {
        val manager =  LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        pg_bar.visibility = View.GONE

        rv_gif.apply {
            layoutManager = manager
            hasFixedSize()
            adapter = GifAdapter(mGifListData)
        }
    }

}