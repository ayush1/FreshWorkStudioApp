package com.example.freshworkassignment.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshworkassignment.R
import com.example.freshworkassignment.adapter.GifAdapter
import com.example.freshworkassignment.callback.FavouriteClickCallback
import com.example.freshworkassignment.eventbus.FavouriteEvent
import com.example.freshworkassignment.model.GifData
import com.example.freshworkassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.layout_trending_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TrendingGifFragment : Fragment(), FavouriteClickCallback {

    private var trendingGifViewModel: MainViewModel? = null
    private var mView : View? = null
    private var mContext : Context? = null
    private var mGifListData : ArrayList<GifData>? = ArrayList()
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
        mView = inflater.inflate(R.layout.layout_trending_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trendingGifViewModel?.getTrendingGifData(0)

        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    trendingGifViewModel?.getSearchGifData(et_search.text.toString(), 0)
                    pg_bar.visibility = View.VISIBLE
                    return true
                }
                return false
            }
        })

        trendingGifViewModel?.mUIResponse?.observe(viewLifecycleOwner,
            { gifList ->
                mGifListData = gifList
                populateGif()
            })

        trendingGifViewModel?.mUIError?.observe(viewLifecycleOwner,
            { error -> populateError(error) })
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
            mAdapter = GifAdapter(mGifListData)
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy > 0) {
                        val layoutmanager = recyclerView.layoutManager as LinearLayoutManager
                        val totalItemCount = layoutmanager.itemCount
                        val visibleItem = recyclerView.childCount
                        val prevVisibleItem = layoutmanager.findFirstVisibleItemPosition()

                        /*if (loading) {
                            if (totalItemCount > previousTotal) {

                                previousTotal = totalItemCount;
                                page++;
                            }
                        }
                        if (!loading
                            && (prevVisibleItem + visibleThreshold + visibleItem) >= totalItemCount) {
                            page++;
                            // call pagination and pass page limit
                            getPagination();
                        }*/
                    }
                }
            })
        }
    }

    @Subscribe
    override fun onFavouriteClicked(event: FavouriteEvent) {
        if(event.isConsumed) return
        trendingGifViewModel?.addOrRemoveFavourite(event.gifData)
        mAdapter?.updateItemView(event.gifData, event.position)
        event.isConsumed = true
    }


}