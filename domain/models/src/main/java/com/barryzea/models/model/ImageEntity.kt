package com.barryzea.models.model

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 8/11/23.
 * Copyright (c)  All rights reserved.
 **/

@Entity
data class ImageEntity(
 @PrimaryKey(autoGenerate = true)
 val id:Long=0,
 val url:String="",
 val description:String="") {
 override fun equals(other: Any?): Boolean {
  if (this === other) return true
  if (javaClass != other?.javaClass) return false

  other as ImageEntity

  if (id != other.id) return false

  return true
 }

 override fun hashCode(): Int {
  return id.hashCode()
 }
}