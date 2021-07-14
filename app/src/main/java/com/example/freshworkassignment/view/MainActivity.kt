package com.example.freshworkassignment.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.freshworkassignment.R
import com.example.freshworkassignment.viewmodel.MainViewModel
import com.example.freshworkassignment.viewpager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.layout_activity_main.*

class MainActivity : AppCompatActivity() {

    private var mViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)

        initViewModel()
        initViews()
    }

    private fun initViews() {
        val mViewPagerAdapter = ViewPagerAdapter(this)
        viewPager.apply {
            adapter = mViewPagerAdapter
        }
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if(position == 0)
                tab.text = getString(R.string.Trending)
            else
                tab.text = getString(R.string.favorite)
        }.attach()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
    }
}