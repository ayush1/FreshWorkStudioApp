package com.example.freshworkassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.freshworkassignment.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var mViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
//        mViewModel?.getTrendingGifData(0)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
    }

}