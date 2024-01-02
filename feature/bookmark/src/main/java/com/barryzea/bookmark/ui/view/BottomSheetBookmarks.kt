package com.barryzea.bookmark.ui.view

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.barryzea.bookmark.databinding.DialogListBookmarkBinding
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.core.R
import com.barryzea.models.model.Tag
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 31/12/2023
 * Copyright (c)  All rights reserved.
 ***/

class BottomSheetBookmarks: BottomSheetDialogFragment() {
    private lateinit var bind:DialogListBookmarkBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val viewModel:BookmarkViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bind = DialogListBookmarkBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(bind.root)

        bind.btnAddBookmark.visibility = View.GONE
        bind.btnClose.visibility = View.VISIBLE
        bind.tvHeader.text=getString(R.string.filter_title)

        bottomSheetBehavior = BottomSheetBehavior.from(bind.root.parent as View)
        bottomSheetBehavior.state= BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object:BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState==BottomSheetBehavior.STATE_EXPANDED){
                       /* bind.btnAddBookmark.visibility = View.GONE
                        bind.btnClose.visibility = View.VISIBLE
                        bind.tvHeader.text=getString(R.string.filter_title)*/
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
        bind.btnClose.setOnClickListener {
           dismiss()
        }
        setUpObservers()
        return bottomSheetDialog
    }


    private fun setUpObservers(){
        viewModel.fetchAllTags()
        viewModel.bookmarks.observe(this){
            it?.let{if(it.isNotEmpty())setUpBookmarksChipGroup(it)}
        }
    }

    private fun setUpBookmarksChipGroup(bookmarks:List<Tag>){
        bind.bookmarkChips.removeAllViews()
        bookmarks.forEach {bookmark->
            val chip= Chip(context).apply {
                id = bookmark.idTag.toInt()
                text=bookmark.description
                chipStrokeWidth = 3F
                if(bookmark.color.isNotEmpty())chipStrokeColor = ColorStateList.valueOf(Color.parseColor(bookmark.color))
                if(bookmark.color.isNotEmpty())rippleColor = ColorStateList.valueOf(Color.parseColor(bookmark.color)).withAlpha(255)
                if(bookmark.color.isNotEmpty())chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(bookmark.color)).withAlpha(160)

                setOnClickListener {
                    viewModel.setBookmarkIdSelectedFilter(bookmark.idTag)
                    dismiss()
                }
            }
            bind.bookmarkChips.addView(chip)

        }
    }
}