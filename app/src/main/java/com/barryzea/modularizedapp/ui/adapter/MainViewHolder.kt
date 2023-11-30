package com.barryzea.modularizedapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.models.model.Note
import com.barryzea.modularizedapp.databinding.ItemLayoutBinding


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 9/11/23.
 * Copyright (c)  All rights reserved.
 **/

class MainViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
 private val bind=ItemLayoutBinding.bind(itemView)
 fun onBind(imageEntity: Note, onItemClick:(Note)->Unit, onItemDelete:(Note)->Unit) = with(bind){
   tvDetail.text=imageEntity.description
     btnDelete.setOnClickListener{onItemDelete(imageEntity)}
     root.setOnClickListener { onItemClick(imageEntity) }
 }
}