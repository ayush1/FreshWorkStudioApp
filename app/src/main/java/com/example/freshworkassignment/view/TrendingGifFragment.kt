package com.example.freshworkassignment.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshworkassignment.R
import com.example.freshworkassignment.adapter.GifAdapter
import com.example.freshworkassignment.callback.FavouriteClickCallback
import com.example.freshworkassignment.eventbus.FavouriteEvent
import com.example.freshworkassignment.model.GifUIModel
import com.example.freshworkassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.layout_trending_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TrendingGifFragment : Fragment(), FavouriteClickCallback {

    private var trendingGifViewModel: MainViewModel? = null
    private var mContext : Context? = null
    private var mGifListData : ArrayList<GifUIModel> = ArrayList()
    private var mAdapter : GifAdapter? = null
    private var isLoading : Boolean = false

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
        return inflater.inflate(R.layout.layout_trending_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()

        trendingGifViewModel?.getTrendingGif()

        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchData()
                    return true
                }
                return false
            }
        })

        trendingGifViewModel?.mUIResponse?.observe(viewLifecycleOwner,
            { gifList ->
                mGifListData.addAll(gifList)
                populateGif()
            })

        trendingGifViewModel?.mUIError?.observe(viewLifecycleOwner,
            { error -> populateError(error) })
    }

    private fun getSearchData() {
        trendingGifViewModel?.searchQuery = et_search.text.toString()
        trendingGifViewModel?.offset = 0
        mGifListData.clear()
        pg_bar.visibility = View.VISIBLE

        trendingGifViewModel?.getSearchGif()
    }

    private fun initViewModel() {
        trendingGifViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }
    }

    private fun initRecyclerview() {
        val mLayoutManager =  LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)

        rv_gif.apply {
            layoutManager = mLayoutManager
            hasFixedSize()
            mAdapter = GifAdapter()
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    //Pagination logic : for trending & search gif
                    if (!isLoading) {
                        if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mGifListData.size - 1) {
                            if(!TextUtils.isEmpty(trendingGifViewModel?.searchQuery))
                                trendingGifViewModel?.getSearchGif()
                            else
                                trendingGifViewModel?.getTrendingGif()

                            isLoading = true
                        }
                    }
                }
            })
        }
    }

    /*
    show error occurred message
     */
    private fun populateError(error: String) {
        if (trendingGifViewModel?.offset == 0) {
            ll_error.visibility = View.VISIBLE
            tv_error_msg.text = error
            et_search.visibility = View.GONE
            fl.visibility = View.GONE
            iv_error.setImageDrawable(mContext?.let {
                ContextCompat.getDrawable(it, R.drawable.ic_no_internet)
            })
            tv_error_title.text = getString(R.string.no_internet_connection)
        }
    }

    /*
    trending/search data : inflate gif data on view
     */
    private fun populateGif() {
        pg_bar.visibility = View.GONE
        isLoading = false
        ll_error.visibility = View.GONE
        fl.visibility = View.VISIBLE
        mAdapter?.setData(mGifListData)
    }

    @Subscribe
    override fun onFavouriteClicked(event: FavouriteEvent) {
        if(event.isConsumed) return
        trendingGifViewModel?.addOrRemoveFavourite(event.gifData)
        mAdapter?.updateItemView(event.gifData, event.position)
        event.isConsumed = true
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}