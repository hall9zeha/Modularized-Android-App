package com.barryzea.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        with(holder.bind){
            if(position==0){
                tvOnBoardTitle.text= "Título primero"
                tvOnBoardContent.text="It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. "
            }
            if(position ==1){
                tvOnBoardTitle.text="Título segundo"
                tvOnBoardContent.text = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. "
            }
            if(position == 2){
                tvOnBoardTitle.text="Título tercero"
                tvOnBoardContent.text="It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. "

            }
        }
    }

    override fun getItemCount() = MAX_STEP
}
class PagerViewHolder(val bind: OnboardDesignBinding):RecyclerView.ViewHolder(bind.root)