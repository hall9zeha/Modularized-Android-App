package com.barryzea.data.repository

import com.barryzea.abstraction.TagRepository
import com.barryzea.data.database.db.MyDatabase
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteTagCrossRef
import com.barryzea.models.model.Tag
import javax.inject.Inject


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 02/12/2023
 * Copyright (c)  All rights reserved.
 ***/

class TagRepositoryImpl @Inject constructor(database: MyDatabase):TagRepository {
    private val dao = database.getDao()
    override suspend fun saveTag(tag: Tag)= dao.saveTag(tag)
    override suspend fun deleteTag(tag: Tag) = dao.deleteTag(tag)
    override suspend fun updateTag(tag: Tag) = dao.updateTag(tag)
    override suspend fun getAllTags() = dao.getAllTag()
    override suspend fun getTagById(id: Long) = dao.getTagById(id)
    //


}