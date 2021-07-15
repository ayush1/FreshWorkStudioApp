package com.example.freshworkassignment.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.freshworkassignment.R
import com.example.freshworkassignment.adapter.GifAdapter
import com.example.freshworkassignment.callback.FavouriteClickCallback
import com.example.freshworkassignment.eventbus.FavouriteEvent
import com.example.freshworkassignment.eventbus.UpdateDataEvent
import com.example.freshworkassignment.itemdecorator.ItemOffsetDecoration
import com.example.freshworkassignment.model.GifUIModel
import com.example.freshworkassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.error_layout.*
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

       favouriteGifViewModel?.getFavouriteGifFromDb()

        favouriteGifViewModel?.favoriteLiveData?.observe(viewLifecycleOwner,
            { gifList ->
                mGifListData = gifList
                populateFavoriteGif()
            })

        favouriteGifViewModel?.emptyFavoriteLiveData?.observe(viewLifecycleOwner,
            { emptyMsg -> populateEmptyOrErrorView(emptyMsg) })
    }

    private fun initViewModel() {
        favouriteGifViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    private fun initRecyclerView() {
        val manager =  GridLayoutManager(mContext, 2)
        val spacing = resources.getDimensionPixelSize(R.dimen.dp15) / 2
        rv_grid_gif.apply {
            layoutManager = manager
            setPadding(spacing, spacing, spacing, spacing)
            addItemDecoration(ItemOffsetDecoration(spacing))
            hasFixedSize()
            mAdapter = GifAdapter()
            adapter = mAdapter
            mAdapter?.setFavouriteAdapter(true)
        }
    }

    /*
    Inflate favorite gifs
     */
    private fun populateFavoriteGif() {
        rv_grid_gif.visibility =  View.VISIBLE
        ll_error.visibility = View.GONE
        mAdapter?.setData(mGifListData)
    }

    /*
    show empty favorite list
     */
    private fun populateEmptyOrErrorView(msg: String) {
        rv_grid_gif.visibility = View.GONE
        ll_error.visibility = View.VISIBLE
        iv_error.setImageDrawable(mContext?.let { ContextCompat.getDrawable(it, R.drawable.img_no_favorite) })
        tv_error_title.text = getString(R.string.no_favorites)
        tv_error_msg.text = msg
    }

    @Subscribe
    override fun onFavouriteClicked(event: FavouriteEvent) {
        if(event.isConsumed || !isVisible) return
        favouriteGifViewModel?.addOrRemoveFavourite(event.gifData)
        val index = favouriteGifViewModel?.getItemIndex(event.gifData)

        //this event is used remove the favorite from the trending/search data
        val updateEvent = UpdateDataEvent(event.gifData, index!!)
        updateEvent.post()

        event.isConsumed = true
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}