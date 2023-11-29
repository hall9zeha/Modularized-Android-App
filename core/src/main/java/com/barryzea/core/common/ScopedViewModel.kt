package com.barryzea.modularizedapp.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 9/11/23.
 * Copyright (c)  All rights reserved.
 **/

abstract class ScopedViewModel:ViewModel(), Scope by Scope.Impl(){
    init{
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}