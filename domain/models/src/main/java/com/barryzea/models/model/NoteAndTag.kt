package com.barryzea.models.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.parcelize.Parcelize


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 15/12/2023
 * Copyright (c)  All rights reserved.
 ***/
@Parcelize
data class NoteAndTag(
    @Embedded var note:Note = Note(),
    @Relation(
        parentColumn =  "idNote",
        entityColumn = "idTag",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    val tags:List<Tag> = arrayListOf()

):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NoteAndTag
        if (note != other.note) return false

        return true
    }

    override fun hashCode(): Int {
        return note.hashCode()
    }
}

