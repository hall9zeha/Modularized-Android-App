package com.barryzea.abstraction

import com.barryzea.models.model.Note


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 28/11/2023
 * Copyright (c)  All rights reserved.
 ***/

interface MainRepository {

    suspend fun saveRegister(entity: Note):Long
    suspend fun updateRegister(entity:Note):Int
    suspend fun deleteRegister(idEntity:Long):Int
    suspend fun getAllRegisters():List<Note>
    suspend fun getRegisterById(id:Long):Note
}