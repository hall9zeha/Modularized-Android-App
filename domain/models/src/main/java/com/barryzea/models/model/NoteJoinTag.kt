package com.barryzea.models.model

import android.graphics.drawable.Drawable.ConstantState
import android.os.Parcelable
import android.provider.SyncStateContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 30/11/2023
 * Copyright (c)  All rights reserved.
 ***/

@Parcelize
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = arrayOf("idNote"),
            childColumns = arrayOf("idJoinNote"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("idTag"),
            childColumns = arrayOf("idJoinTag"),
            onDelete = ForeignKey.CASCADE
        )

    ],
    indices = [
        Index("idJoinNote"),
        Index("idJoinTag")
    ]
)
data class NoteJoinTag(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id")val id:Long=0,
    @ColumnInfo("idJoinNote") val idJoinNote:Long=0,
    @ColumnInfo("idJoinTag")val idJoinTag:Long=0,
):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NoteJoinTag

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
