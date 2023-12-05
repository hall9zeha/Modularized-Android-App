package com.barryzea.navigation


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 05/12/2023
 * Copyright (c)  All rights reserved.
 ***/

sealed class NavigationFlow{
    data object HomeFlow:NavigationFlow()
    data object BookmarkFlow:NavigationFlow()
}