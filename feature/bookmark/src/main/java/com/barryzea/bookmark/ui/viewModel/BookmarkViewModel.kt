package com.barryzea.bookmark.ui.viewModel

import androidx.lifecycle.ViewModel
import com.barryzea.modularizedapp.ui.common.ScopedViewModel


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 02/12/2023
 * Copyright (c)  All rights reserved.
 ***/

class BookmarkViewModel:ScopedViewModel() {

    init{
        initScope()
    }


    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}