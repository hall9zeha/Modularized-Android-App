package com.barryzea.data.repository

import com.barryzea.abstraction.MainRepository
import com.barryzea.data.database.db.MyDatabase
import com.barryzea.models.model.ImageEntity
import javax.inject.Inject


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

class MainRepositoryImpl @Inject constructor(database: MyDatabase): MainRepository {
    private val dao = database.getDao()
    override suspend fun saveRegister(entity: ImageEntity) = dao.saveRegister(entity)


    override suspend fun updateRegister(entity: ImageEntity) = dao.updateRegister(entity)

    override suspend fun deleteRegister(idEntity: Long) = dao.deleteRegister(idEntity)

    override suspend fun getAllRegisters(): List<ImageEntity> = dao.getAllRegisters()
    override suspend fun getRegisterById(id: Long) = dao.getRegisterById(id)
}