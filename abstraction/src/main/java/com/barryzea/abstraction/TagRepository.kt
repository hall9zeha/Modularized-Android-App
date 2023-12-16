package com.barryzea.abstraction

import com.barryzea.models.model.NoteTagCrossRef
import com.barryzea.models.model.Tag


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 02/12/2023
 * Copyright (c)  All rights reserved.
 ***/

interface TagRepository {
    //For Tags
    suspend fun saveTag(tag: Tag):Long
    suspend fun deleteTag(tag: Tag):Int
    suspend fun updateTag(tag: Tag):Int
    suspend fun getAllTags():List<Tag>
    suspend fun getTagById(id:Long):Tag
    //
    suspend fun saveNoteTagCrossRef(noteJoinTag: NoteTagCrossRef):Long
    suspend fun deleteNoteTagCrossRef(noteJoinTag: NoteTagCrossRef):Int

}