package com.barryzea.models.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Parcelize
@Entity
data class Note(
 @PrimaryKey(autoGenerate = true)
 @ColumnInfo(name = "idNote", index = true)
 val idNote:Long=0,
 val url:String="",
 val description:String=""):Parcelable {
 override fun equals(other: Any?): Boolean {
  if (this === other) return true
  if (javaClass != other?.javaClass) return false

  other as Note

  if (idNote != other.idNote) return false

  return true
 }

 override fun hashCode(): Int {
  return idNote.hashCode()
 }
}