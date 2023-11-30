package com.barryzea.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.barryzea.models.model.Note


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Dao
interface MyDao {
    @Transaction
    @Insert
    suspend fun saveRegister(entity:Note):Long
    @Transaction
    @Update
    suspend fun updateRegister(entity: Note):Int
    @Transaction
    @Query("delete from Note where idNote=:id")
    suspend fun deleteRegister(id:Long):Int
    @Query("select * from Note")
    suspend fun getAllRegisters():List<Note>

    @Query("select * from Note where idNote=:id")
    suspend fun getRegisterById(id:Long):Note

}