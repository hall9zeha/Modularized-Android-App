package com.barryzea.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.barryzea.models.model.ImageEntity


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Dao
interface MyDao {
    @Transaction
    @Insert
    suspend fun saveRegister(entity:ImageEntity):Long
    @Transaction
    @Update
    suspend fun updateRegister(entity: ImageEntity):Int
    @Transaction
    @Query("delete from ImageEntity where id=:id")
    suspend fun deleteRegister(id:Long):Int
    @Query("select * from ImageEntity")
    suspend fun getAllRegisters():List<ImageEntity>

    @Query("select * from ImageEntity where id=:id")
    suspend fun getRegisterById(id:Long):ImageEntity

}