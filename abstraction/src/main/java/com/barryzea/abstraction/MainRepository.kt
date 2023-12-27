package com.barryzea.abstraction

import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.models.model.NoteTagCrossRef
import com.barryzea.models.model.Tag


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 28/11/2023
 * Copyright (c)  All rights reserved.
 ***/

interface MainRepository {

    suspend fun saveRegister(entity: Note):Long
    suspend fun updateRegister(entity:Note):Int
    suspend fun deleteRegister(idEntity:Long):Int
    suspend fun getAllRegisters():List<NoteAndTag>
    suspend fun getRegisterById(id:Long):NoteAndTag
    suspend fun fetchNoteAndTagsByTagId(idTag:Long):List<NoteAndTag>
    suspend fun saveNoteTagCrossRef(noteJoinTag: NoteTagCrossRef):Long
    suspend fun deleteNoteTagCrossRef(noteJoinTag: NoteTagCrossRef):Int

}