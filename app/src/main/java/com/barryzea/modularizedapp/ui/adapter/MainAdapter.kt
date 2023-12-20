package com.barryzea.modularizedapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.modularizedapp.R


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 9/11/23.
 * Copyright (c)  All rights reserved.
 **/

class MainAdapter(private val onItemClick:(NoteAndTag)->Unit, private val onItemDelete:(NoteAndTag)->Unit):RecyclerView.Adapter<MainViewHolder>() {
 private var registerList = mutableListOf<NoteAndTag>()
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
  val context = parent.context
  val itemView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent, false)
  return MainViewHolder(itemView)
 }
 fun addAll(registers:List<NoteAndTag>){
  registers.forEach { addItem(it) }
 }
 fun addItem(entity:NoteAndTag){
  if(!registerList.contains(entity)){
   registerList.add(0,entity)
   notifyItemInserted(0)
  }else{
   update(entity)
  }
 }
 fun removeItem(entity: NoteAndTag){
  if(registerList.contains(entity)){
   val index = registerList.indexOf(entity)
   registerList.remove(entity)
   notifyItemRemoved(index)
  }
 }
 fun clear(){
  if(registerList.isNotEmpty()){
   val size=registerList.size
   registerList.clear()
   notifyItemRangeRemoved(0,size)
  }
 }
 private fun update(entity:NoteAndTag){
  val index = registerList.indexOf(entity)
  registerList[index] = entity
  notifyItemChanged(index)
 }
 override fun getItemCount() = registerList.size

 override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
  holder.onBind(registerList[position], onItemClick, onItemDelete)
 }

}