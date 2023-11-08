package com.barryzea.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barryzea.data.database.dao.MyDao
import com.barryzea.models.model.ImageEntity


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Database(entities = [ImageEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase:RoomDatabase() {
 abstract fun getDao(): MyDao
}