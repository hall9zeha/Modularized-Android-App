package com.barryzea.core.common

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.barryzea.core.entities.PrefsEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 23/12/2023
 * Copyright (c)  All rights reserved.
 ***/

const val USER_PREFERENCES_NAME = "userPreferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

class DataStorePreferences @Inject constructor(private val context: Context){
    companion object{
        val ON_BOARDING_COMPLETED = booleanPreferencesKey("onBoardingCompleted")
    }
    suspend fun saveToDataStore(prefsEntity: PrefsEntity){
        context.dataStore.edit {
            it[ON_BOARDING_COMPLETED] = prefsEntity.completedOnboarding
        }
    }
    fun getFromDataStore() = context.dataStore.data.map {
        PrefsEntity(
            completedOnboarding = it[ON_BOARDING_COMPLETED]?:false
        )
    }
    suspend fun clearDataStore() = context.dataStore.edit{
        it.clear()
    }
}
@Module()
@InstallIn(SingletonComponent::class)
class CoreModule{
    @Singleton
    @Provides
    fun contextProvides(app:Application):Context = app.applicationContext
}
