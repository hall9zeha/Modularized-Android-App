package com.barryzea.modularizedapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}