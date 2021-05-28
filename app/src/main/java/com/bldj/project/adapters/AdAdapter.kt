package com.bldj.project.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.R
import com.bldj.project.databinding.AdsViewHolderBinding
import data.Advert

class AdAdapter(ads: List<Advert>) : RecyclerView.Adapter<AdAdapter.AdViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        val adBinding: AdsViewHolderBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.ads_view_holder, parent, false)
        return AdViewHolder(adBinding)
//            return AdViewHolder(
//                LayoutInflater.from(parent.context)
//                    .inflate(R.layout.ads_view_holder, parent, false)
//            )
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bindAd(ads[position])
    }

    private var ads: List<Advert> = ads
    private var layoutInflater: LayoutInflater? = null

    inner class AdViewHolder(adbinding_: AdsViewHolderBinding) :
        RecyclerView.ViewHolder(adbinding_.root) {
        var adBinding: AdsViewHolderBinding = adbinding_
        fun bindAd(ad: Advert) {
            adBinding?.ad = ad
            adBinding?.executePendingBindings()

            //if (adBinding?.root != null)
//                itemView.setOnClickListener {
//                    adsListener.onEventClicked(ad)
//                }
        }
    }
}