package com.barryzea.modularizedapp.ui.views

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.bookmark.ui.view.AddBookmarkDialog
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.core.R
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteJoinTag
import com.barryzea.models.model.Tag
import com.barryzea.modularizedapp.databinding.DetailScreenDialogBinding

import com.barryzea.modularizedapp.ui.common.EXTRA_KEY
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 10/11/23.
 * Copyright (c)  All rights reserved.
 **/

@AndroidEntryPoint
class NewRegisterDialog: DialogFragment(){
    private var entity:Note?=null
    private var _bind: DetailScreenDialogBinding?=null
    private var isNewRegister:Boolean = true
    private var bookmarkByNote:MutableList<Long> = arrayListOf()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var isExpanded=false
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
                if(isExpanded){
                    bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                }else {
                    maintenanceRegister()
                }
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
        setUpBottomSheet()
        setUpObservers()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireActivity().onBackPressedDispatcher.addCallback(this, object :OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    if(isExpanded){
                        bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                    }else {
                        maintenanceRegister()
                    }
                }
            })
        }
    }
   private fun setUpMenuProvider(){
       bind.toolbarDetail.inflateMenu(R.menu.note_menu)
       bind.toolbarDetail.setOnMenuItemClickListener {
           when(it.itemId){
               R.id.itemTag-> {
                   if(isExpanded) bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                   else bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED

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
        bookmarkViewModel.fetchAllTags()
        viewModel.idOfRegisterInserted.observe(this){idNote->
            viewModel.setRegisterId(idNote!!)
            if(bookmarkByNote.isNotEmpty()){
                bookmarkByNote.forEach {idBookmark->
                    bookmarkViewModel.saveNoteJoinTag(NoteJoinTag(idJoinNote = idNote, idJoinTag = idBookmark))
                }
            }
            dismiss()
        }
        viewModel.updatedRegisterRow.observe(this){
            viewModel.setRegisterId(entity!!.idNote)
            if(bookmarkByNote.isNotEmpty()){
                bookmarkByNote.forEach { idBookmark->
                    bookmarkViewModel.saveNoteJoinTag(NoteJoinTag(idJoinNote = entity!!.idNote, idJoinTag = idBookmark )) }
            }
            dismiss()
        }
        bookmarkViewModel.bookmarkById.observe(this){
            it?.let{
                addBookmark(it)
                bookmarkViewModel.fetchAllTags()
                Log.e("BOOKMARK_IN_NOTE", it.toString() )
            }
        }

        bookmarkViewModel.bookmarks.observe(this){
            it?.let{setUpBookmarksChipGroup(it)}
        }
    }
    private fun setUpBottomSheet(){
        bind.bottomSheetListBookmark.tvHeader.text = getString(R.string.bookmark_title)
        bottomSheetBehavior = BottomSheetBehavior.from(bind.bottomSheetListBookmark.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(object:BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_EXPANDED->isExpanded=true
                    BottomSheetBehavior.STATE_COLLAPSED->isExpanded=false
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
        //Listeners
        bind.bottomSheetListBookmark.btnAddBookmark.setOnClickListener{
            AddBookmarkDialog().show(childFragmentManager.beginTransaction(), AddBookmarkDialog::class.simpleName)
            bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
        }
    }
    private fun setUpBookmarksChipGroup(bookmarks:List<Tag>){
        bind.bottomSheetListBookmark.bookmarkChips.removeAllViews()
        bookmarks.forEach {bookmark->
            val chip=Chip(context).apply {
                id = bookmark.idTag.toInt()
                text=bookmark.description
                chipStrokeWidth = 3F
                if(bookmark.color.isNotEmpty())chipStrokeColor = ColorStateList.valueOf(Color.parseColor(bookmark.color))
                if(bookmark.color.isNotEmpty())rippleColor = ColorStateList.valueOf(Color.parseColor(bookmark.color)).withAlpha(255)
                if(bookmark.color.isNotEmpty())chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(bookmark.color)).withAlpha(160)

                setOnClickListener {
                    //bookmarkViewModel.getBookmarkById(bookmark.idTag)
                    addBookmark(bookmark)
                    bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                }
            }
            bind.bottomSheetListBookmark.bookmarkChips.addView(chip)

        }
    }
    private fun addBookmark(bookmark:Tag){
        val tag = Chip(context).apply{
            id = bookmark.idTag.toInt()
            chipStrokeColor = ColorStateList.valueOf(Color.parseColor(bookmark.color))
            rippleColor = ColorStateList.valueOf(Color.parseColor(bookmark.color))
            chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(bookmark.color)).withAlpha(160)
            text = bookmark.description
        }
        val chip = bind.chipGroupTags.findViewById<Chip>(bookmark.idTag.toInt())
        if(bind.chipGroupTags.indexOfChild(chip) == -1){
            bookmarkByNote.add(bookmark.idTag)
            bind.chipGroupTags.addView(tag)
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