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
import com.example.freshworkassignment.itemdecorator.GridItemDecoration
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.layout_favourite_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FavouriteGifFragment : Fragment(), FavouriteClickCallback {

    private var favouriteGifViewModel: MainViewModel? = null
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

        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.layout_favourite_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGifListData = favouriteGifViewModel?.favouriteGifList
        populateGif()

        favouriteGifViewModel?.favouriteLiveData?.observe(viewLifecycleOwner,
            { gifList ->
//                mGifListData = gifList
                rv_grid_gif.adapter?.notifyDataSetChanged()
            })

        favouriteGifViewModel?.mUIError?.observe(viewLifecycleOwner,
            { error -> populateError(error) })
    }

    private fun populateGif() {
        val manager =  GridLayoutManager(mContext, 2)
        progress_bar.visibility = View.GONE

        rv_grid_gif.apply {
            layoutManager = manager
            hasFixedSize()
            addItemDecoration(GridItemDecoration())
            adapter = GifAdapter()
            (adapter as GifAdapter).setFavaouriteAdapter(true)
        }
    }

    private fun populateError(error: String) {
        Log.d("Fragment_response", error)
    }

    private fun initViewModel() {
        favouriteGifViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    @Subscribe
    override fun onFavouriteClicked(event: FavouriteEvent) {
        if(event.isConsumed) return
        favouriteGifViewModel?.addOrRemoveFavourite(event.gifData)
        event.isConsumed = true
    }

}