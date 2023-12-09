package com.barryzea.bookmark.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.barryzea.data.repository.TagRepositoryImpl
import com.barryzea.models.model.Tag
import com.barryzea.modularizedapp.ui.common.ScopedViewModel
import com.barryzea.modularizedapp.ui.common.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 02/12/2023
 * Copyright (c)  All rights reserved.
 ***/
@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repository:TagRepositoryImpl):ScopedViewModel() {
    private var _bookmarks:SingleMutableLiveData<List<Tag>> = SingleMutableLiveData()
    val bookmarks:SingleMutableLiveData<List<Tag>> get() = _bookmarks

    private var _idTagInserted:SingleMutableLiveData<Long> = SingleMutableLiveData()
    val idTagInserted:SingleMutableLiveData<Long> get() = _idTagInserted

    private var _tagRowDeleted:SingleMutableLiveData<Int>  = SingleMutableLiveData()
    val tagRowDeleted:SingleMutableLiveData<Int> get() = _tagRowDeleted

    private var _colorSelected:SingleMutableLiveData<String> = SingleMutableLiveData()
    val colorSelected:SingleMutableLiveData<String> get() = _colorSelected
    init{
        initScope()
    }

    fun fetchAllTags(){
        launch{
            _bookmarks.postValue(repository.getAllTags())
        }
    }
    fun saveTag(tag:Tag){
        launch { _idTagInserted.postValue(repository.saveTag(tag)) }
    }
    fun deleteTag(tag:Tag){
        launch{_tagRowDeleted.postValue(repository.deleteTag(tag))}
    }
    fun setColorSelected(colorHex:String){
        launch { _colorSelected.value= colorHex}
    }
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}