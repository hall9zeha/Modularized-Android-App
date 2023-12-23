package com.barryzea.onboarding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.barryzea.core.R
import com.barryzea.core.common.DataStorePreferences
import com.barryzea.core.entities.PrefsEntity
import com.barryzea.onboarding.adapter.MAX_STEP
import com.barryzea.onboarding.adapter.OnBoardAdapter
import com.barryzea.onboarding.databinding.OnboardMainLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 22/12/2023
 * Copyright (c)  All rights reserved.
 ***/

@AndroidEntryPoint
class OnBoardFragment: Fragment() {
    private var _bind:OnboardMainLayoutBinding? = null
    @Inject
    lateinit var dataStore:DataStorePreferences
    private val bind:OnboardMainLayoutBinding get() = _bind!!
    private val onBackPressedCallback = object:OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            _bind = OnboardMainLayoutBinding.inflate(inflater, container,false)
            _bind?.let{
                return it.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.viewPagerMain.adapter = OnBoardAdapter()
        TabLayoutMediator(bind.onBoardTab,bind.viewPagerMain){tab,position->}.attach()
        setUpListeners()
        activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)
    }
    private fun setUpListeners(){
        bind.viewPagerMain.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateContentButtons(position)
            }
        })
        bind.btnSkip.setOnClickListener {

            findNavController().navigate(com.barryzea.navigation.R.id.home_flow)}
        bind.btnNext.setOnClickListener {
            if(bind.btnNext.text.toString()==getString(R.string.get_started))findNavController().navigate(com.barryzea.navigation.R.id.home_flow)
            else{
                val currentPage= (bind.viewPagerMain.currentItem) + 1
                bind.viewPagerMain.currentItem = currentPage
                updateContentButtons(currentPage)
            }
        }
    }
    private fun updateContentButtons(position:Int){
        if(position == MAX_STEP-1){
            bind.btnNext.text = getString(R.string.get_started)
            bind.btnNext.contentDescription = getString(R.string.get_started)
        }else{
            bind.btnNext.text = getString(R.string.next)
            bind.btnNext.contentDescription = getString(R.string.next)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.launch {
            dataStore.saveToDataStore(PrefsEntity(completedOnboarding = true))
        }
        _bind = null
    }
}
