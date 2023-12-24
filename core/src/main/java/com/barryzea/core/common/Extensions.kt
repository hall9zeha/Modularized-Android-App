package com.barryzea.core.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 24/12/2023
 * Copyright (c)  All rights reserved.
 ***/

fun ImageView.loadUrl(url:String)=Glide
    .with(this.context)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .centerCrop()
    .into(this)
fun ImageView.loadUrl(resource:Int)=Glide
    .with(this.context)
    .load(resource)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .centerCrop()
    .into(this)