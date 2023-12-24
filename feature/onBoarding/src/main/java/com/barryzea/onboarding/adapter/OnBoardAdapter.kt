package com.barryzea.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.core.common.loadUrl
import com.barryzea.onboarding.R
import com.barryzea.core.R as coreRes
import com.barryzea.onboarding.databinding.OnboardDesignBinding

/****
 * Project ModularizedApp
 * Created by Barry Zea H. on 22/12/2023
 * Copyright (c)  All rights reserved.
 ***/
const val MAX_STEP=3
class OnBoardAdapter: RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(OnboardDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) = holder.itemView.run{
        val animation = AnimationUtils.loadAnimation(holder.bind.root.context, coreRes.anim.animation_item_onboarding)
        holder.itemView.startAnimation(animation)
        with(holder.bind){
            if(position==0){
                tvOnBoardTitle.text= context.getString(coreRes.string.first_screen_sample_title)
                tvOnBoardContent.text="It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. "
                ivOnboardMain.loadUrl(coreRes.drawable.onboard_screen1)
            }
            if(position ==1){
                tvOnBoardTitle.text= context.getString(coreRes.string.second_screen_sample_title)
                tvOnBoardContent.text = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. "
                ivOnboardMain.loadUrl(coreRes.drawable.onboard_screen2)
            }
            if(position == 2){
                tvOnBoardTitle.text= context.getString(coreRes.string.third_screen_sample_title)
                tvOnBoardContent.text="It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. "
                ivOnboardMain.loadUrl(coreRes.drawable.onboard_screen3)
            }
        }
    }
    override fun getItemCount() = MAX_STEP
}
class PagerViewHolder(val bind: OnboardDesignBinding):RecyclerView.ViewHolder(bind.root)