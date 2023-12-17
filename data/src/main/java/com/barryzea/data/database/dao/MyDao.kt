package com.barryzea.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.models.model.NoteTagCrossRef
import com.barryzea.models.model.Tag


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
    @Transaction
    @Query("select * from Note")
    suspend fun getAllRegisters():List<NoteAndTag>

    @Transaction
    @Query("select * from Note where idNote=:id")
    suspend fun getRegisterById(id:Long):NoteAndTag

    //For Tags
    //@Transaction
    @Insert
    suspend fun saveTag(tag:Tag):Long
    @Transaction
    @Update
    suspend fun updateTag(tag:Tag):Int
    @Transaction
    @Delete
    suspend fun deleteTag(tag:Tag):Int
    @Transaction
    @Query("select * from Tag")
    suspend fun getAllTag():List<Tag>

    @Transaction
    @Query("select * from Tag where idTag=:id")
    suspend fun getTagById(id:Long):Tag

    //For inner join note and tag
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNoteTagCrossRef(noteJoinTag:NoteTagCrossRef):Long

    @Transaction
    @Delete
    suspend fun deleteNoteTagCrossRef(noteJoinTag: NoteTagCrossRef):Int

    //Obtenemos todas las notas unidas a determinado marcador
    @Transaction
    @Query("SELECT * FROM Note WHERE idNote IN (SELECT idNote FROM NoteTagCrossRef WHERE idTag = :idTag)")
    suspend fun fetchNotesAndTagByTagId(idTag:Long):List<NoteAndTag>
}