package com.barryzea.bookmark.ui.view

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.core.R
import com.barryzea.bookmark.databinding.DialogListBookmarkBinding
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.models.model.Tag
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 12/12/2023
 * Copyright (c)  All rights reserved.
 ***/

@AndroidEntryPoint
class BookmarkListDialog: DialogFragment() {
    private var _bind:DialogListBookmarkBinding?=null
    private val bind:DialogListBookmarkBinding get() = _bind!!
    private val viewModel:BookmarkViewModel by viewModels(ownerProducer = {requireParentFragment()})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.myFullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            _bind = DialogListBookmarkBinding.inflate(inflater, container,false)
            _bind?.let{return it.root}
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.tvHeader.text = getString(R.string.bookmark_title)
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners() {
        bind.btnAddBookmark.setOnClickListener {
            //Ya que abrimos el díalogo desde este principal que también es un diálogo
            //para evitar que se cierren los dos al llamar a dismiss()
            //llamamos al primero con childFragmentManager y a este segundo con parentFragmentManager
            //así no pierden el enlace al propietario de la instancia viewModel donde tenemos los observadores
            //y no se cierran ambos al llamar a dismiss()
            AddBookmarkDialog().show(parentFragmentManager.beginTransaction(),AddBookmarkDialog::class.simpleName)
            dismiss()
        }
    }

    private fun setUpObservers(){
        viewModel.fetchAllTags()
        viewModel.bookmarks.observe(this){
            it?.let{setUpBookmarksChipGroup(it)}
        }
    }
    private fun setUpBookmarksChipGroup(bookmarks:List<Tag>){
        bookmarks.forEach {bookmark->
            val chip=Chip(context).apply {
                id = bookmark.idTag.toInt()
                text=bookmark.description
                chipStrokeWidth = 3F
                if(bookmark.color.isNotEmpty())chipStrokeColor = ColorStateList.valueOf(Color.parseColor(bookmark.color))
                if(bookmark.color.isNotEmpty())rippleColor = ColorStateList.valueOf(Color.parseColor(bookmark.color)).withAlpha(255)
                setOnClickListener {
                    viewModel.getBookmarkById(bookmark.idTag)
                }
            }
            bind.bookmarkChips.addView(chip)

        }
    }

}