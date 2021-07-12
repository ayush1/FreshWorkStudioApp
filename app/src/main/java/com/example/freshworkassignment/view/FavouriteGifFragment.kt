package com.example.freshworkassignment.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.freshworkassignment.R
import com.example.freshworkassignment.adapter.GifAdapter
import com.example.freshworkassignment.callback.FavouriteClickCallback
import com.example.freshworkassignment.eventbus.FavouriteEvent
import com.example.freshworkassignment.model.GifUIModel
import com.example.freshworkassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.layout_favourite_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FavouriteGifFragment : Fragment(), FavouriteClickCallback {

    private var favouriteGifViewModel: MainViewModel? = null
    private var mContext : Context? = null
    private var mGifListData : ArrayList<GifUIModel> = ArrayList()
    private var mAdapter : GifAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()

        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_favourite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        mGifListData = favouriteGifViewModel?.getFavouriteGifFromDb() ?: ArrayList()
        populateGif()

        favouriteGifViewModel?.favouriteLiveData?.observe(viewLifecycleOwner,
            { gifList ->
                mGifListData = gifList
                populateGif()
            })

        favouriteGifViewModel?.mUIError?.observe(viewLifecycleOwner,
            { error -> populateError(error) })
    }

    private fun initViewModel() {
        favouriteGifViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    private fun initRecyclerView() {
        val manager =  GridLayoutManager(mContext, 2)
        rv_grid_gif.apply {
            layoutManager = manager
            hasFixedSize()
            mAdapter = GifAdapter()
            adapter = mAdapter
            mAdapter?.setFavouriteAdapter(true)
        }
    }

    private fun populateGif() {
        progress_bar.visibility = View.GONE
        mAdapter?.setData(mGifListData)
    }

    private fun populateError(error: String) {
        Log.d("Fragment_response", error)
    }

    @Subscribe
    override fun onFavouriteClicked(event: FavouriteEvent) {
        if(event.isConsumed) return
        favouriteGifViewModel?.addOrRemoveFavourite(event.gifData)
        event.isConsumed = true
    }

}