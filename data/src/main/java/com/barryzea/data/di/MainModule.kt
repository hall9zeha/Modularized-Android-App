package com.barryzea.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.barryzea.abstraction.MainRepository
import com.barryzea.abstraction.TagRepository
import com.barryzea.data.database.db.MyDatabase
import com.barryzea.data.repository.MainRepositoryImpl
import com.barryzea.data.repository.TagRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

private const val MY_DATABASE_NAME = "MyDatabase"
@Module
@InstallIn(SingletonComponent::class)
class MainModule{
    @Provides
    @Singleton
    fun databaseProvides(app:Application)= Room.databaseBuilder(app.applicationContext,
        MyDatabase::class.java,
        MY_DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract  fun noteRepositoryProvides(repository: MainRepositoryImpl): MainRepository
    @Binds
    abstract fun  tagRepositoryProvides(tagRepository: TagRepositoryImpl):TagRepository
}