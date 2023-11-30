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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private  val  viewModel:MainViewModel by viewModels()
    private lateinit var bind:ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var staggeredGrid:StaggeredGridLayoutManager
    private lateinit var entity: Note

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

        setUpAdapter()
        setUpViewModel()
        setUpListeners()
    }
    private fun setUpViewModel(){
        viewModel.fetchAllRegisters()
        viewModel.allRegisters.observe(this){
            Log.e("TAG", it.toString() )
            adapter.addAll(it!!)
        }
        viewModel.registerId.observe(this){
            viewModel.getRegisterById(it!!)
        }
        viewModel.entity.observe(this){
            adapter.addItem(it!!)
        }
        viewModel.deletedRegisterRow.observe(this){
            adapter.removeItem(entity)
        }
    }
    private fun setUpAdapter(){
        staggeredGrid = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = MainAdapter(::onItemClick,::onItemDelete)
        bind.rvMain.apply {
            setHasFixedSize(true)
            layoutManager=staggeredGrid
            adapter=this@MainActivity.adapter
        }
        setupScrollList()
    }
    private fun setupScrollList(){
        bind.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    if(bind.addFab.isShown)bind.addFab.hide()
                }else if (dy<0){
                    if(!bind.addFab.isShown)bind.addFab.show()
                }
            }
        })
    }
    private fun setUpListeners(){
        bind.addFab.setOnClickListener {
            NewRegisterDialog().show(supportFragmentManager.beginTransaction(),NewRegisterDialog::class.java.simpleName)
        }
    }
    private fun onItemClick(entity:Note){
        NewRegisterDialog.newInstance(entity).show(supportFragmentManager.beginTransaction(),NewRegisterDialog::class.java.simpleName)
    }
    private fun onItemDelete(entity: Note){
        this.entity=entity
        viewModel.deleteRegister(entity.idNote)
    }
}