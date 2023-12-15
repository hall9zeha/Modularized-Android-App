package com.barryzea.models.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.parcelize.Parcelize


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 30/11/2023
 * Copyright (c)  All rights reserved.
 ***/
//Tabla de relación muchos a muchos (o tabla de referecias cruzadas)
@Parcelize
@Entity(
    primaryKeys = ["idNote","idTag"],
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = arrayOf("idNote"),
            childColumns = arrayOf("idNote"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("idTag"),
            childColumns = arrayOf("idTag"),
            onDelete = ForeignKey.CASCADE
        )

    ],
    indices = [
        Index("idNote"),
        Index("idTag")
    ]
)
data class NoteTagCrossRef(
    @ColumnInfo("idNote") val idJoinNote:Long=0,
    @ColumnInfo("idTag")val idJoinTag:Long=0,
):Parcelable


