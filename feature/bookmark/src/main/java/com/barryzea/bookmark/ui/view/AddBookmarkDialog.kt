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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let{ac->
            dialogView = onCreateView(LayoutInflater.from(requireContext()),null,savedInstanceState)
            dialogView?.let{ _bind = DialogBookmarkAddBinding.bind(it)}

            dialogView?.let{onViewCreated(it,savedInstanceState)}
            val builder = MaterialAlertDialogBuilder(ac)
                .setView(dialogView)
                .setPositiveButton("Accept",null)
                .setNegativeButton("Cancel",null)
                .create()
            builder.setOnShowListener(this)
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
        setUpObservers(bind)
        bind.btnSelectColor.setOnClickListener {
            showColorSelectDialog()
        }


    }

    override fun onShow(dialog: DialogInterface?) {
        dialog?.let {
            positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                saveBookmark()
            }
        }
    }

    private fun setUpObservers(bind: DialogBookmarkAddBinding) {
        viewModel.colorSelected.observe(this){
            bind.tvColorDesc.text = it
            bind.tvColorDesc.setBackgroundColor(Color.parseColor(it))

        }
        viewModel.idTagInserted.observe(this){
            viewModel.getBookmarkById(it!!)
            dismiss()
        }
    }
    private fun saveBookmark(){
        if(bind.edtBookmark.text!!.isNotEmpty()){
            bookmark=Tag(description = bind.edtBookmark.text.toString(),color=bind.tvColorDesc.text.toString())
            viewModel.saveTag(bookmark)

        }else{
            bind.edtBookmark.error = "Tiene que escribir algo"
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