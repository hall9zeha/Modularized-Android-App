package com.barryzea.abstraction

import com.barryzea.models.model.ImageEntity


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 28/11/2023
 * Copyright (c)  All rights reserved.
 ***/

interface MainRepository {

    suspend fun saveRegister(entity: ImageEntity):Long
    suspend fun updateRegister(entity:ImageEntity):Int
    suspend fun deleteRegister(idEntity:Long):Int
    suspend fun getAllRegisters():List<ImageEntity>
    suspend fun getRegisterById(id:Long):ImageEntity
}