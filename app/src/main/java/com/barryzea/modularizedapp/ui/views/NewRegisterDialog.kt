package com.barryzea.modularizedapp.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.models.model.ImageEntity
import com.barryzea.modularizedapp.R
import com.barryzea.modularizedapp.databinding.DetailScreenDialogBinding
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 10/11/23.
 * Copyright (c)  All rights reserved.
 **/

@AndroidEntryPoint
class NewRegisterDialog: DialogFragment(){
    private var bind:DetailScreenDialogBinding?=null
    private val viewModel:MainViewModel by viewModels(ownerProducer = {requireActivity()})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.myFullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            bind= DetailScreenDialogBinding.inflate(inflater,container,false)
            bind?.let{b->
                b.toolbarDetail.setNavigationIcon(R.drawable.ic_back)
                b.toolbarDetail.setNavigationOnClickListener {
                    val entity = ImageEntity(description = b.tvContent.text.toString())
                    saveRegister(entity)
                }
                b.toolbarDetail.title = "Nuevo registro"
                setUpObservers()
                return b.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    private fun setUpObservers(){
        viewModel.idOfRegisterInserted.observe(this){
            viewModel.setRegisterId(it!!)
            dismiss()
        }

    }
    private fun saveRegister(entity:ImageEntity){
        viewModel.saveRegister(entity)
    }
}