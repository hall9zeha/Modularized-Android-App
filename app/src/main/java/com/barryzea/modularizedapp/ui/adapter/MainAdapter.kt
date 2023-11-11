package com.barryzea.modularizedapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.barryzea.models.model.ImageEntity
import com.barryzea.modularizedapp.R


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 9/11/23.
 * Copyright (c)  All rights reserved.
 **/

class MainAdapter(private val onItemClick:(ImageEntity)->Unit, private val onItemDelete:(ImageEntity)->Unit):RecyclerView.Adapter<MainViewHolder>() {
 private var registerList = mutableListOf<ImageEntity>()
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
  val context = parent.context
  val itemView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent, false)
  return MainViewHolder(itemView)
 }
 fun addAll(registers:List<ImageEntity>){
  registers.forEach { addItem(it) }
 }
 fun addItem(entity:ImageEntity){
  if(!registerList.contains(entity)){
   registerList.add(entity)
   notifyItemInserted(registerList.size -1)
  }else{
   update(entity)
  }
 }
 fun removeItem(entity: ImageEntity){
  if(registerList.contains(entity)){
   val index = registerList.indexOf(entity)
   registerList.remove(entity)
   notifyItemRemoved(index)
  }
 }
 private fun update(entity:ImageEntity){
  val index = registerList.indexOf(entity)
  registerList[index] = entity
  notifyItemChanged(index)
 }
 override fun getItemCount() = registerList.size

 override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
  holder.onBind(registerList[position], onItemClick, onItemDelete)
 }

}