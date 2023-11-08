package com.barryzea.data.repository

import com.barryzea.models.model.ImageEntity


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

class MainRepository : MainRepositoryInterface {
    override suspend fun saveRegister(entity: ImageEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateRegister(entity: ImageEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRegister(entity: ImageEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRegisters(): List<ImageEntity> {
        TODO("Not yet implemented")
    }
}