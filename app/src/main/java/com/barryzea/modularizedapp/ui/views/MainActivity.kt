package com.barryzea.modularizedapp.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.barryzea.modularizedapp.R
import com.barryzea.core.R as coreRes
import com.barryzea.modularizedapp.databinding.ActivityMainBinding
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import com.barryzea.navigation.NavigationFlow
import com.barryzea.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),ToFlowNavigatable {
    private lateinit var bind:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
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
        setUpObservers()
        //setUpNavigator()
        changeActionbarTitle()
        onBackPressedDispatcher()

        }
    private fun setUpToolbar(){
        setSupportActionBar(bind.toolbarMain)
    }
    private fun setUpObservers(){
        viewModel.onBoardingCompleted.observe(this){onBoardingCompleted->
            setUpNavigator(onBoardingCompleted)
        }
    }
    private fun setUpNavigator(onBoardingCompleted: Boolean?) {
        //Si es la primera vez que se abre la app mostramos las panatallas de on boarding, cambiando el el destino de inicio
        //del gráfico de navegación por el de onboardFragment
       // val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        if(!onBoardingCompleted!!) {
            val navGraph: NavGraph =
                navController.navInflater.inflate(com.barryzea.navigation.R.navigation.main_nav_graph)
            navGraph.setStartDestination(com.barryzea.navigation.R.id.on_board_flow)
            navController.graph = navGraph
            bind.navView.setupWithNavController(navController)
            NavigationUI.setupWithNavController(bind.navView, navController)
        }else{
            bind.navView.setupWithNavController(navController)
            NavigationUI.setupWithNavController(bind.navView, navController)
        }
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
                R.id.homeFragment -> {supportActionBar?.title = getString(coreRes.string.home_title);bind.toolbarMain.visibility = View.VISIBLE}
                com.barryzea.bookmark.R.id.bookmarkFragment ->{ supportActionBar?.title = getString(coreRes.string.bookmarks);bind.toolbarMain.visibility = View.VISIBLE}
                com.barryzea.onboarding.R.id.onboardFragment->{bind.toolbarMain.visibility = View.GONE}
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
    private fun onBackPressedDispatcher(){
        onBackPressedDispatcher.addCallback(this,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
              handleBackPressed()
            }
        })
    }

}