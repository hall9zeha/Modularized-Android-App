package com.barryzea.modularizedapp.ui.views

import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.barryzea.bookmark.ui.view.AddBookmarkDialog
import com.barryzea.bookmark.ui.viewModel.BookmarkViewModel
import com.barryzea.core.R
import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.models.model.NoteTagCrossRef
import com.barryzea.models.model.Tag
import com.barryzea.modularizedapp.databinding.DetailScreenDialogBinding

import com.barryzea.modularizedapp.ui.common.EXTRA_KEY
import com.barryzea.modularizedapp.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 10/11/23.
 * Copyright (c)  All rights reserved.
 **/

@AndroidEntryPoint
class NewRegisterDialog: DialogFragment(){
    private var entity:NoteAndTag?=null
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
                   //Ocultamos el teclado si está activo
                   val inputMethodManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                   inputMethodManager.hideSoftInputFromWindow(view?.windowToken,0)
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
            entity?.tags?.forEach {bookmark->
                addBookmark(bookmark,false)
            }
           isNewRegister=false
       }
      setUpDetail(entity?.note)
    }
    private fun setUpDetail(entity: Note?){
        entity?.let{bind.tvContent.setText(entity.description)}
        bind.toolbarDetail.title = if(isNewRegister) getString(R.string.new_note) else getString(R.string.edit_note)
    }
    private fun setUpObservers(){
        bookmarkViewModel.fetchAllTags()
        viewModel.idOfRegisterInserted.observe(this){idNote->
            if(bookmarkByNote.isNotEmpty()){
                bookmarkByNote.forEach {idBookmark->
                   viewModel.saveNoteJoinTag(NoteTagCrossRef(idJoinNote = idNote!!, idJoinTag = idBookmark))
                }
            }
            viewModel.setRegisterId(idNote!!)
            dismiss()
        }
        viewModel.updatedRegisterRow.observe(this){
            if(bookmarkByNote.isNotEmpty()){
                bookmarkByNote.forEach { idBookmark->
                    viewModel.saveNoteJoinTag(NoteTagCrossRef(idJoinNote = entity!!.note.idNote, idJoinTag = idBookmark )) }
            }
            viewModel.setRegisterId(entity!!.note.idNote)
            dismiss()
        }
        bookmarkViewModel.bookmarkById.observe(this){
            it?.let{
                addBookmark(it,true)
                bookmarkViewModel.fetchAllTags()
                Log.e("BOOKMARK_IN_NOTE", it.toString() )
            }
        }

        bookmarkViewModel.bookmarks.observe(this){
            it?.let{setUpBookmarksChipGroup(it)}
        }
        viewModel.bookmarkDeleteRow.observe(this){
            it?.let{row->
                if(row>0) Toast.makeText(context,
                    getString(R.string.deleted_msg), Toast.LENGTH_SHORT).show()

            }
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
                    addBookmark(bookmark,true)
                    bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                }
            }
            bind.bottomSheetListBookmark.bookmarkChips.addView(chip)

        }
    }
    private fun addBookmark(bookmark:Tag, isNew:Boolean){
        val tag = Chip(context).apply{
            id = bookmark.idTag.toInt()
            chipStrokeColor = ColorStateList.valueOf(Color.parseColor(bookmark.color))
            rippleColor = ColorStateList.valueOf(Color.parseColor(bookmark.color))
            chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(bookmark.color)).withAlpha(160)
            text = bookmark.description
            setOnLongClickListener {chip->
                viewModel.deleteNoteTagCrossRef(NoteTagCrossRef(idJoinNote = entity?.note?.idNote!!, idJoinTag = bookmark.idTag))
                bind.chipGroupTags.removeView(chip)
                true
            }
            setOnClickListener {
                Snackbar.make(bind.root,R.string.long_press_msg,Snackbar.LENGTH_SHORT).show()
            }
        }
        if(isNew) {
            val chip = bind.chipGroupTags.findViewById<Chip>(bookmark.idTag.toInt())
            if (bind.chipGroupTags.indexOfChild(chip) == -1) {
                bookmarkByNote.add(bookmark.idTag)
                bind.chipGroupTags.addView(tag)
            }
        }else{
            bind.chipGroupTags.addView(tag)
        }
    }
    private fun maintenanceRegister(){
        if(isNewRegister) {
            val entity = Note(description = bind.tvContent.text.toString())
            saveRegister(entity)}
        else{
            val updateEntity= Note(idNote= entity!!.note.idNote,
                description = bind.tvContent.text.toString(),
                url = entity!!.note.url)
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
        fun newInstance(param1:NoteAndTag)=NewRegisterDialog().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_KEY, param1)
            }
        }
    }
}