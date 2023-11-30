package com.barryzea.models.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.barryzea.modularizedapp.ui.common.EXTRA_KEY
import kotlinx.parcelize.Parcelize


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 30/11/2023
 * Copyright (c)  All rights reserved.
 ***/

@Parcelize
@Entity
data class Tag(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("idTag", index=true) val idTag:Long=0,
    @ColumnInfo("description") val description:String="",
    @ColumnInfo("color") val color:String=""
):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tag

        if (idTag != other.idTag) return false

        return true
    }

    override fun hashCode(): Int {
        return idTag.hashCode()
    }
}