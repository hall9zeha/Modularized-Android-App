package com.barryzea.data.repository

import com.barryzea.models.model.ImageEntity


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

interface MainRepositoryInterface {
 suspend fun saveRegister(entity: ImageEntity):Long
 suspend fun updateRegister(entity:ImageEntity):Int
 suspend fun deleteRegister(idEntity:Long):Int
 suspend fun getAllRegisters():List<ImageEntity>
 suspend fun getRegisterById(id:Long):ImageEntity
}