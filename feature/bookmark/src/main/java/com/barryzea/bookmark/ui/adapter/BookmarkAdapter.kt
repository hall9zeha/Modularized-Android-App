package com.barryzea.bookmark.ui.adapter

import android.content.res.ColorStateList
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

class BookmarkAdapter(private val onLongClick:(bookmark:Tag)->Unit, private val onClick:()->Unit):RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
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
        bookmarkList.forEach{add(it) }
    }
    fun add(bookmark:Tag){
        if(!bookmarks.contains(bookmark)){

            //Insertamos el item en la primera posición
            //Esto duplicará el ìndice cero dos veces, así que si queremos usar un evento que requiera
            //de la posición real del item en el adaptador debemos usar getAdapterPosition() y no solamente position
            bookmarks.add(0,bookmark)// -> Opcional
            //notifyItemInserted(bookmarks.size - 1)
            notifyItemInserted(0)

        }else{
            update(bookmark)
        }
    }
    private fun update(bookmark:Tag){
        if(bookmarks.contains(bookmark)){
            val index = bookmarks.indexOf(bookmark)
            bookmarks[index] = bookmark
            notifyItemChanged(index)
        }
    }
    fun remove(bookmark:Tag){
        if(bookmarks.contains(bookmark)){
            val index = bookmarks.indexOf(bookmark)
            bookmarks.remove(bookmark)
            notifyItemRemoved(index)
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val bind = ItemBookmarkBinding.bind(itemView)
        fun onBind(tag:Tag) = with(bind){
            tvDescription.text = tag.description
            cardViewTag.strokeColor = Color.parseColor(tag.color)
            cardViewTag.strokeWidth = 3
            cardViewTag.rippleColor = ColorStateList.valueOf(Color.parseColor(tag.color)).withAlpha(255)
            ivColor.setBackgroundColor(Color.parseColor(tag.color))

            root.setOnClickListener { onClick() }
            root.setOnLongClickListener { onLongClick(tag); true }
        }
    }
}