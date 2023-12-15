package com.barryzea.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barryzea.data.database.dao.MyDao
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteTagCrossRef
import com.barryzea.models.model.Tag


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Database(entities = [Note::class, Tag::class, NoteTagCrossRef::class], version = 1, exportSchema = false)
abstract class MyDatabase:RoomDatabase() {
 abstract fun getDao(): MyDao
}