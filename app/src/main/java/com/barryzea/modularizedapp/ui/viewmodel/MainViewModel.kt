package com.barryzea.modularizedapp.ui.viewmodel

import com.barryzea.data.repository.MainRepositoryImpl

import com.barryzea.models.model.Note
import com.barryzea.models.model.NoteAndTag
import com.barryzea.modularizedapp.ui.common.ScopedViewModel
import com.barryzea.modularizedapp.ui.common.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 9/11/23.
 * Copyright (c)  All rights reserved.
 **/

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepositoryImpl): ScopedViewModel() {
    private var _allRegisters:SingleMutableLiveData<List<NoteAndTag>> = SingleMutableLiveData()
    val allRegisters:SingleMutableLiveData<List<NoteAndTag>> = _allRegisters

    private var _entity:SingleMutableLiveData<NoteAndTag> = SingleMutableLiveData()
    val entity:SingleMutableLiveData<NoteAndTag> = _entity

    private var _noteAndTagByTagId:SingleMutableLiveData<List<NoteAndTag>> = SingleMutableLiveData()
    val noteAndTagByTagId:SingleMutableLiveData<List<NoteAndTag>> = _noteAndTagByTagId

    private var _idOfRegisterInserted:SingleMutableLiveData<Long> = SingleMutableLiveData()
    val idOfRegisterInserted:SingleMutableLiveData<Long> = _idOfRegisterInserted

    private var _updatedRegisterRow:SingleMutableLiveData<Int> = SingleMutableLiveData()
    val updatedRegisterRow:SingleMutableLiveData<Int> = _updatedRegisterRow

    private var _deletedRegisterRow:SingleMutableLiveData<Int> = SingleMutableLiveData()
    val deletedRegisterRow:SingleMutableLiveData<Int> = _deletedRegisterRow

    private var _registerId:SingleMutableLiveData<Long> = SingleMutableLiveData()
    val registerId:SingleMutableLiveData<Long> = _registerId

    init {
        initScope()
    }
    fun fetchAllRegisters(){
        launch {_allRegisters.value =repository.getAllRegisters()}
    }
    fun getRegisterById(id: Long){
        launch { _entity.value=repository.getRegisterById(id) }
    }
    fun getNoteAndTagByTagId(tagId:Long){
        launch{_noteAndTagByTagId.value = repository.fetchNoteAndTagsByTagId(tagId)}
    }
    fun saveRegister(entity: Note){
        launch {_idOfRegisterInserted.value = repository.saveRegister(entity) }
    }
    fun updateRegister(entity: Note){
        launch { _updatedRegisterRow.value = repository.updateRegister(entity) }
    }
    fun deleteRegister(idEntity: Long){
        launch { _deletedRegisterRow.value = repository.deleteRegister(idEntity) }
    }
    fun setRegisterId(id:Long){
       launch {  _registerId.value = id}
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}