package com.barryzea.modularizedapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.barryzea.modularizedapp.R
import com.barryzea.modularizedapp.databinding.ActivityMainBinding
import com.barryzea.modularizedapp.ui.adapter.MainAdapter
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private  val  viewModel:MainViewModel by viewModels()
    private lateinit var bind:ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var staggeredGrid:StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpAdapter()
        setUpViewModel()
    }


    private fun setUpViewModel(){
        viewModel.allRegisters.observe(this){
            adapter.addAll(it)
        }
    }
    private fun setUpAdapter(){
        staggeredGrid = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = MainAdapter()
        bind.rvMain.apply {
            setHasFixedSize(true)
            layoutManager=staggeredGrid
            adapter=this@MainActivity.adapter
        }
    }
}