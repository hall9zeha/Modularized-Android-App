package com.barryzea.models.model

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Entity
data class ImageEntity(
 @PrimaryKey(autoGenerate = true)
 val id:Long=0,
 val url:String="",
 val description:String="") {
}