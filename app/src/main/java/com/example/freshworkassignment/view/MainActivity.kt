package com.example.freshworkassignment.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
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
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if(position == 0)
                tab.text = "Trending"
            else
                tab.text = "Favourite"
        }.attach()

    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
    }

}