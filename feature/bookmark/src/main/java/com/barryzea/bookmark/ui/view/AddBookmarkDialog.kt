package com.barryzea.bookmark.ui.view

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.bookmark.R
import com.barryzea.bookmark.databinding.DialogBookmarkAddBinding
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import dagger.hilt.android.AndroidEntryPoint


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 09/12/2023
 * Copyright (c)  All rights reserved.
 ***/

@AndroidEntryPoint
class AddBookmarkDialog: DialogFragment() {
    private var _bind:DialogBookmarkAddBinding ?=null
    private val bind:DialogBookmarkAddBinding get() = _bind!!

    private var dialogView:View? = null
    private val viewModel:BookmarkViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let{ac->
            dialogView = onCreateView(LayoutInflater.from(requireContext()),null,savedInstanceState)
            dialogView?.let{onViewCreated(it,savedInstanceState)}
            val builder = MaterialAlertDialogBuilder(ac)
                .setView(dialogView)
                .setPositiveButton("Accept") { dialog, _ ->
                    dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dismiss()
                }
                .create()
            return builder
        }
        return super.onCreateDialog(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bookmark_add,container,false)
    }

    override fun getView(): View? {
        return dialogView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = DialogBookmarkAddBinding.bind(view)
        setUpObservers(bind)
        bind.btnSelectColor.setOnClickListener {
            showColorSelectDialog()
        }

    }
    private fun setUpObservers(bind: DialogBookmarkAddBinding) {
        viewModel.colorSelected.observe(this){
            bind.tvColorDesc.text = it
            bind.tvColorDesc.setBackgroundColor(Color.parseColor(it))
        }
    }
    private fun showColorSelectDialog() {
        ColorPickerDialog.Builder(context)
            .setTitle("Selecciona un color")
            .setPreferenceName("colorPicker")
            .setPositiveButton("Ok", object:ColorEnvelopeListener{
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                    Log.e("COLOR-SELECTED", "setUpColorSelectDialog: ${envelope?.color}" )
                    Log.e("COLOR-HEX", "setUpColorSelectDialog: ${envelope?.hexCode}" )

                    viewModel.setColorSelected("#"+envelope?.hexCode.toString())

                }

            })
            .setNegativeButton("Cancel"){dialog,i->
                dialog.dismiss()
            }
            .attachAlphaSlideBar(true)
            .attachBrightnessSlideBar(true)
            .setBottomSpace(12)
            .show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}