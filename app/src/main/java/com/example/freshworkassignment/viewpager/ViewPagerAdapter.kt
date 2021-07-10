package com.example.freshworkassignment.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.freshworkassignment.view.FavouriteGifFragment
import com.example.freshworkassignment.view.TrendingGifFragment

class ViewPagerAdapter(mActivity: FragmentActivity)
    : FragmentStateAdapter(mActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0) {
            return TrendingGifFragment()
        }
        return FavouriteGifFragment()
    }
}