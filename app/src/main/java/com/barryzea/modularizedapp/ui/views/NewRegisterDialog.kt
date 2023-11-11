package com.barryzea.modularizedapp.ui.views

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.models.model.ImageEntity
import com.barryzea.modularizedapp.R
import com.barryzea.modularizedapp.databinding.DetailScreenDialogBinding
import com.barryzea.modularizedapp.ui.common.EXTRA_KEY
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 10/11/23.
 * Copyright (c)  All rights reserved.
 **/
private const val ARG_PARAM1 = "param1"
@AndroidEntryPoint
class NewRegisterDialog: DialogFragment(){
    private var entity:ImageEntity?=null
    private var _bind:DetailScreenDialogBinding?=null
    private var isNewRegister:Boolean = true

    private val bind:DetailScreenDialogBinding get() = _bind!!
    private val viewModel:MainViewModel by viewModels(ownerProducer = {requireActivity()})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.myFullScreenDialog)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object:Dialog(requireActivity(),theme){
            override fun onBackPressed() {

              maintenanceRegister()
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            _bind= DetailScreenDialogBinding.inflate(inflater,container,false)
            _bind?.let{ b->
                b.toolbarDetail.setNavigationIcon(R.drawable.ic_back)
                b.toolbarDetail.setNavigationOnClickListener {
                   maintenanceRegister()
                }
                b.toolbarDetail.title = "Nuevo registro"
                getIntentExtras()
                setUpObservers()
                return b.root
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().onBackPressedDispatcher.addCallback(this, object :OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    maintenanceRegister()
                }
            })
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    private fun getIntentExtras(){
       arguments?.let {
            entity = it.getParcelable(EXTRA_KEY)!!
            setUpDetail(entity!!)
            isNewRegister=false
        }
    }
    private fun setUpDetail(entity: ImageEntity){
        bind.tvContent.setText(entity.description)
    }
    private fun setUpObservers(){
        viewModel.idOfRegisterInserted.observe(this){
            viewModel.setRegisterId(it!!)
            dismiss()
        }
        viewModel.updatedRegisterRow.observe(this){
            viewModel.setRegisterId(entity!!.id)
            dismiss()
        }
    }
    private fun maintenanceRegister(){
        if(isNewRegister) {
            val entity = ImageEntity(description = bind.tvContent.text.toString())
            saveRegister(entity)}
        else{
            val updateEntity= ImageEntity(id= entity!!.id,
                description = bind.tvContent.text.toString(),
                url = entity!!.url)
            updateRegister(updateEntity)
        }
    }
    private fun saveRegister(entity:ImageEntity){
        viewModel.saveRegister(entity)
    }
    private fun updateRegister(entity: ImageEntity){
        viewModel.updateRegister(entity)
    }
    companion object{
        @JvmStatic
        fun newInstance(param1:ImageEntity)=NewRegisterDialog().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_KEY, param1)
            }
        }
    }
}