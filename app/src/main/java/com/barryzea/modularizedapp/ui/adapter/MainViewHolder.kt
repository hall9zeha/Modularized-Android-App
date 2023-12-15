package com.barryzea.modularizedapp.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.ImageView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.modularizedapp.databinding.ItemLayoutBinding


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 9/11/23.
 * Copyright (c)  All rights reserved.
 **/

class MainViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
 private val bind=ItemLayoutBinding.bind(itemView)
 fun onBind(imageEntity: NoteAndTag, onItemClick:(NoteAndTag)->Unit, onItemDelete:(NoteAndTag)->Unit) = with(bind){
     tvDetail.text = imageEntity.note.description
     lnTags.removeAllViews()
     imageEntity.tags.forEach {tag->
         val shapeView = ShapeDrawable(OvalShape())
         shapeView.paint.color = Color.parseColor(tag.color)
         shapeView.intrinsicHeight = 20
         shapeView.intrinsicWidth = 20

         var iv = ImageView(root.context)
         iv.setPadding(8,0,0,0)
         iv.setImageDrawable(shapeView)

         lnTags.addView(iv)
     }
     btnDelete.setOnClickListener { onItemDelete(imageEntity) }
     root.setOnClickListener { onItemClick(imageEntity) }
 }
}