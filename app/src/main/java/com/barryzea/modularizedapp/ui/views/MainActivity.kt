package com.barryzea.modularizedapp.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.barryzea.models.model.Note
import com.barryzea.modularizedapp.databinding.ActivityMainBinding
import com.barryzea.modularizedapp.ui.adapter.MainAdapter
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import com.barryzea.modularizedapp.ui.views.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                setKeepOnScreenCondition{false}

            }
        }
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpFragment()

    }
    private fun setUpFragment(){
        supportFragmentManager.beginTransaction()
            .add(bind.contentMain.id,HomeFragment())
            .commit()
    }

}