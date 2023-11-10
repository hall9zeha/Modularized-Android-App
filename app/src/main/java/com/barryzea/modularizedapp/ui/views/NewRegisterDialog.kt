package com.barryzea.modularizedapp.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.barryzea.modularizedapp.R
import com.barryzea.modularizedapp.databinding.DetailScreenDialogBinding


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 10/11/23.
 * Copyright (c)  All rights reserved.
 **/

class NewRegisterDialog: DialogFragment(){
    private var bind:DetailScreenDialogBinding?=null
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
                b.toolbarDetail.setNavigationOnClickListener { dismiss() }
                b.toolbarDetail.title = "Nuevo registro"
                return b.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}