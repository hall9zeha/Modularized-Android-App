package com.barryzea.modularizedapp.ui.views

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.bookmark.ui.view.BookmarkListDialog
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.core.R
import com.barryzea.models.model.Note
import com.barryzea.modularizedapp.databinding.DetailScreenDialogBinding
import com.barryzea.modularizedapp.ui.common.EXTRA_KEY
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 10/11/23.
 * Copyright (c)  All rights reserved.
 **/

@AndroidEntryPoint
class NewRegisterDialog: DialogFragment(){
    private var entity:Note?=null
    private var _bind:DetailScreenDialogBinding?=null
    private var isNewRegister:Boolean = true
    private val bind:DetailScreenDialogBinding get() = _bind!!
    //Para que la instancia del view model sea la misma que en la actividad principal
    //y así usar los mismos observadores usamos (ownerProducers = {requireActivity})
    //Si el dialogFragment es lanzado desde un Fragment usamos  (ownerProducers = {requireParentFragment})
    private val viewModel:MainViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private val bookmarkViewModel:BookmarkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.myFullScreenDialog)

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
                b.toolbarDetail.setNavigationIcon(R.drawable.left_arrow)
                b.toolbarDetail.setNavigationOnClickListener {
                   maintenanceRegister()
                }
                b.toolbarDetail.title = getString(R.string.new_note)
                return b.root
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMenuProvider()
        getIntentExtras()
        setUpObservers()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().onBackPressedDispatcher.addCallback(this, object :OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    maintenanceRegister()
                }
            })
        }
    }
   private fun setUpMenuProvider(){
       bind.toolbarDetail.inflateMenu(R.menu.note_menu)
       bind.toolbarDetail.setOnMenuItemClickListener {
           when(it.itemId){
               R.id.itemTag-> {
                   BookmarkListDialog().show(childFragmentManager.beginTransaction(), BookmarkListDialog::class.simpleName)
               }
           }
           true
       }
       //No infla el menú en el toolbar del dialog fragment
      /* val menuHost:MenuHost = requireActivity()
       menuHost.addMenuProvider(object:MenuProvider{
           override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
               menuInflater.inflate(R.menu.note_menu,menu)
           }

           override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return true
           }

       },viewLifecycleOwner, Lifecycle.State.RESUMED)*/
   }
    private fun getIntentExtras(){
       arguments?.let {
            entity = it.getParcelable(EXTRA_KEY)!!
            setUpDetail(entity!!)
            isNewRegister=false
        }
    }
    private fun setUpDetail(entity: Note){
        bind.tvContent.setText(entity.description)
    }
    private fun setUpObservers(){

        viewModel.idOfRegisterInserted.observe(this){
            viewModel.setRegisterId(it!!)
            dismiss()
        }
        viewModel.updatedRegisterRow.observe(this){
            viewModel.setRegisterId(entity!!.idNote)
            dismiss()
        }
        bookmarkViewModel.bookmarkById.observe(this){
            it?.let{
                Log.e("BOOKMARK_IN_NOTE", it.toString() )
            }
        }
    }
    private fun maintenanceRegister(){
        if(isNewRegister) {
            val entity = Note(description = bind.tvContent.text.toString())
            saveRegister(entity)}
        else{
            val updateEntity= Note(idNote= entity!!.idNote,
                description = bind.tvContent.text.toString(),
                url = entity!!.url)
            updateRegister(updateEntity)
        }
    }
    private fun saveRegister(entity:Note){
        if(bind.tvContent.text.isNotEmpty())viewModel.saveRegister(entity) else dismiss()
    }
    private fun updateRegister(entity: Note){
        viewModel.updateRegister(entity)
    }
    companion object{
        @JvmStatic
        fun newInstance(param1:Note)=NewRegisterDialog().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_KEY, param1)
            }
        }
    }
}