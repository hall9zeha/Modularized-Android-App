package com.barryzea.modularizedapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.barryzea.data.repository.MainRepository
import com.barryzea.models.model.ImageEntity
import com.barryzea.modularizedapp.ui.common.ScopedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Project ModularizedApp
 * Created by Barry Zea H. on 9/11/23.
 * Copyright (c)  All rights reserved.
 **/

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository): ScopedViewModel() {
    private var _allRegisters:MutableLiveData<List<ImageEntity>> = MutableLiveData()
    val allRegisters:LiveData<List<ImageEntity>> = _allRegisters

    private var _idOfRegisterInserted:MutableLiveData<Long> = MutableLiveData()
    val idOfRegisterInserted:LiveData<Long> = _idOfRegisterInserted

    private var _updatedRegisterRow:MutableLiveData<Int> = MutableLiveData()
    val updatedRegisterRow:LiveData<Int> = _updatedRegisterRow

    private var _deletedRegisterRow:MutableLiveData<Int> = MutableLiveData()
    val deletedRegisterRow:LiveData<Int> = _deletedRegisterRow
    init {
        initScope()
    }
    fun fetchAllRegisters(){
        launch {_allRegisters.value =repository.getAllRegisters()}
    }
    fun saveRegister(entity: ImageEntity){
        launch {_idOfRegisterInserted.value = repository.saveRegister(entity) }
    }
    fun updateRegister(entity: ImageEntity){
        launch { _updatedRegisterRow.value = repository.updateRegister(entity) }
    }
    fun deleteRegister(idEntity: Long){
        launch { _deletedRegisterRow.value = repository.deleteRegister(idEntity) }
    }

}