package com.example.freshworkassignment.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshworkassignment.R
import com.example.freshworkassignment.adapter.GifAdapter
import com.example.freshworkassignment.callback.FavouriteClickCallback
import com.example.freshworkassignment.callback.UpdateDataCallback
import com.example.freshworkassignment.common.Constants
import com.example.freshworkassignment.eventbus.FavouriteEvent
import com.example.freshworkassignment.eventbus.UpdateDataEvent
import com.example.freshworkassignment.model.GifUIModel
import com.example.freshworkassignment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.layout_trending_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TrendingGifFragment : Fragment(), FavouriteClickCallback, UpdateDataCallback {

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

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(TextUtils.isEmpty(s.toString())) {
                    trendingGifViewModel?.searchQuery = ""
                    mGifListData.clear()
                    trendingGifViewModel?.getTrendingGif()
                }
            }

        })

        trendingGifViewModel?.mUIResponse?.observe(viewLifecycleOwner,
            { gifList ->
                if(gifList.size == 0){
                    populateEmptyView(Constants.EmptyEnum.NO_SEARCH_DATA_FOUND.msg)
                } else {
                    mGifListData.addAll(gifList)
                    populateGif()
                }
            })

        trendingGifViewModel?.mUIError?.observe(viewLifecycleOwner,
            { error -> populateErrorView(error) })
    }

    private fun getSearchData() {
        trendingGifViewModel?.searchQuery = et_search.text.toString()
        trendingGifViewModel?.offset = 0
        mGifListData.clear()
        pg_bar.visibility = View.VISIBLE

        hideKeyboard()
        trendingGifViewModel?.getSearchGif()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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

    private fun populateEmptyView(msg: String) {
        ll_error.visibility = View.VISIBLE
        tv_error_msg.text = msg
        fl.visibility = View.GONE
        iv_error.setImageDrawable(mContext?.let {
            ContextCompat.getDrawable(it, R.drawable.no_data_found)
        })
        tv_error_title.text = getString(R.string.no_data_found)
    }

    /*
    show error occurred message
     */
    private fun populateErrorView(error: String) {
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
        et_search.visibility = View.VISIBLE
        isLoading = false
        ll_error.visibility = View.GONE
        fl.visibility = View.VISIBLE
        mAdapter?.setData(mGifListData)
    }

    @Subscribe
    override fun onFavouriteClicked(event: FavouriteEvent) {
        if(event.isConsumed || !isVisible) return
        trendingGifViewModel?.addOrRemoveFavourite(event.gifData)
        mAdapter?.updateItemView(event.gifData, event.position)
        event.isConsumed = true
    }

    @Subscribe
    override fun onUpdateData(event: UpdateDataEvent) {
        mAdapter?.updateItemView(event.gifData, event.position)
        event.isConsumed = true
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}