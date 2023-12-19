package com.barryzea.modularizedapp.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.barryzea.models.model.Note
import com.barryzea.modularizedapp.R
import com.barryzea.modularizedapp.databinding.ActivityMainBinding
import com.barryzea.modularizedapp.ui.adapter.MainAdapter
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import com.barryzea.modularizedapp.ui.views.fragments.HomeFragment
import com.barryzea.navigation.NavigationFlow
import com.barryzea.navigation.Navigator
import com.barryzea.navigation.ToFlowNavigatable
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),ToFlowNavigatable {
    private lateinit var bind:ActivityMainBinding
    private val navController: NavController by lazy{ Navigation.findNavController(this, R.id.nav_host_fragment)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                setKeepOnScreenCondition{false}

            }
        }

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setUpToolbar()
        setUpNavigator()
        changeActionbarTitle()
        //onBackPressedDispatcher()

        }
    private fun setUpToolbar(){

        setSupportActionBar(bind.toolbarMain)
    }
    private fun setUpNavigator(){
        bind.navView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(bind.navView,navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        val drawerLayout = bind.mainDrawerLayout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
    private fun changeActionbarTitle(){
        navController.addOnDestinationChangedListener{_, destination,_ ->
            when(destination.id){
               R.id.homeFragment -> supportActionBar?.title= "Home"
                com.barryzea.bookmark.R.id.bookmarkFragment->supportActionBar?.title = "Bookmarks"
            }
        }
    }
    override fun navigateToFlow(flow: NavigationFlow) {
       // navigator.navigateToFlow(flow)
    }

    //Función que se utiliza en  fragmentHome al sobresscribir el evento onBackPressed
    fun handleBackPressed(){
        if (bind.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            bind.mainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val currentDestinationId = navController.currentDestination?.id
            // Si estamos en el fragmento inicial (HomeFragment), salimos de la actividad
            if (currentDestinationId == R.id.homeFragment) {
                finish()
            } else {
                // Si estamos en otro fragmento, navegamos hacia atrás
                navController.navigateUp()
            }
        }
    }
    //Ya que estamos sobreecribiendo el evento backPressed en fragmentHome no usaremos esta función aquí
    private fun onBackPressedDispatcher(){
        onBackPressedDispatcher.addCallback(this,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
              handleBackPressed()
            }
        })
    }

}