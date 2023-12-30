package com.barryzea.bookmark.ui.view


import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.bookmark.R
import com.barryzea.core.R as core_res
import com.barryzea.bookmark.databinding.DialogBookmarkAddBinding
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.models.model.Tag
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
class AddBookmarkDialog: DialogFragment(), DialogInterface.OnShowListener {
    private var _bind:DialogBookmarkAddBinding ?=null
    private val bind:DialogBookmarkAddBinding get() = _bind!!
    private var dialogView:View? = null
    private val viewModel:BookmarkViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private lateinit var positiveButton:Button
    private lateinit var bookmark:Tag

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let { ac ->
            _bind = DialogBookmarkAddBinding.inflate(LayoutInflater.from(activity))

            _bind?.let {
                val builder = MaterialAlertDialogBuilder(ac,core_res.style.CustomMaterialDialogStyle)
                    .setView(dialogView)
                    .setPositiveButton(getString(core_res.string.accept), null)
                    .setNegativeButton(getString(core_res.string.cancel), null)
                    .setView(it.root)
                    .create()
                builder.setOnShowListener(this)
                return builder
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onShow(dialog: DialogInterface?) {
        dialog?.let {
            positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                saveBookmark()
            }
        }
        setUpObservers()
        bind.btnSelectColor.setOnClickListener {
            showColorSelectDialog()
        }
    }
    private fun setUpObservers() {
        viewModel.idTagInserted.observe(this){
            it?.let{viewModel.getBookmarkById(it!!)}
            dismiss()
        }
    }
    private fun saveBookmark(){
        if(bind.edtBookmark.text!!.isNotEmpty()){
            bookmark=Tag(description = bind.edtBookmark.text.toString(),
                color=if(bind.tvColorDesc.text.isNotEmpty()) bind.tvColorDesc.text.toString() else "#323232")
            viewModel.saveTag(bookmark)

        }else{
            bind.edtBookmark.error = getString(core_res.string.empty_desc_error)
        }
    }
    private fun showColorSelectDialog() {
        ColorPickerDialog.Builder(context)
            .setTitle(getString(core_res.string.select_color))
            .setPreferenceName("colorPicker")
            .setPositiveButton(getString(core_res.string.ok), object:ColorEnvelopeListener{
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {

                    Log.e("COLOR-SELECTED", "setUpColorSelectDialog: ${envelope?.color}" )
                    Log.e("COLOR-HEX", "setUpColorSelectDialog: ${envelope?.hexCode}" )
                    val selectedColorHex="#"+envelope?.hexCode.toString()
                    bind.tvColorDesc.text = selectedColorHex
                    bind.tvColorDesc.setBackgroundColor(Color.parseColor(selectedColorHex))

                }
            })
            .setNegativeButton(getString(core_res.string.cancel)){ dialog, i->
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