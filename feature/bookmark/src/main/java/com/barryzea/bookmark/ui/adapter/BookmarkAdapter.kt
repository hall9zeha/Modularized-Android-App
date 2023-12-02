package com.barryzea.bookmark.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.bookmark.R
import com.barryzea.bookmark.databinding.ItemBookmarkBinding
import com.barryzea.models.model.Tag


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 02/12/2023
 * Copyright (c)  All rights reserved.
 ***/

class BookmarkAdapter:RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    private var bookmarks:MutableList<Tag> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_bookmark,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(bookmarks[position])
    }

    override fun getItemCount()= bookmarks.size
    fun addAll(bookmarkList:List<Tag>){
        bookmarkList.forEach{
            if(!bookmarks.contains(it)){
                bookmarks.add(it)
                notifyItemInserted(bookmarks.size - 1)
            }else{
                update(it)
            }
        }
    }
    private fun update(bookmark:Tag){
        if(bookmarks.contains(bookmark)){
            val index = bookmarks.indexOf(bookmark)
            bookmarks[index] = bookmark
            notifyItemChanged(index)
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val bind = ItemBookmarkBinding.bind(itemView)
        fun onBind(tag:Tag) = with(bind){
            tvDescription.text = tag.description
            cardViewTag.setCardBackgroundColor(Color.parseColor(tag.color))
        }
    }
}