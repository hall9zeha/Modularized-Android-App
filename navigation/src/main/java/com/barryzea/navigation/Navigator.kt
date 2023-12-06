package com.barryzea.navigation

import androidx.navigation.NavController


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 05/12/2023
 * Copyright (c)  All rights reserved.
 ***/

class Navigator{
    lateinit var navController:NavController

    /*fun navigateToFlow(navigationFlow: NavigationFlow) = when(navigationFlow){
        //MainNavGraphDirections requiere el plugin de safe-args para navigation
        NavigationFlow.HomeFlow ->navController.navigate(MainNavGraphDirections.actionHomeFlow())
        NavigationFlow.BookmarkFlow -> navController.navigate(MainNavGraphDirections.actionBookmarkFlow())
    }*/
}