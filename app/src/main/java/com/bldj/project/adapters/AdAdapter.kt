package com.bldj.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.R
import com.bldj.project.databinding.AdsViewHolderBinding
import data.Advert

class AdAdapter(val onBeTravellerClicked: (ad: Advert) -> Unit) :
    RecyclerView.Adapter<AdAdapter.AdViewHolder>() {

    private lateinit var ads: List<Advert>

    //Property for ads List field.
    var adsProperty: List<Advert>
        get() = ads
        set(value) {
            ads = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        val adBinding: AdsViewHolderBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.ads_view_holder, parent, false)
        return AdViewHolder(adBinding)
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bindAd(ads[position])
    }

    private var layoutInflater: LayoutInflater? = null

    inner class AdViewHolder(adbinding_: AdsViewHolderBinding) :
        RecyclerView.ViewHolder(adbinding_.root) {
        var adBinding: AdsViewHolderBinding = adbinding_
        fun bindAd(ad: Advert) {
            adBinding.ad = ad
            adBinding.executePendingBindings()
            adBinding.adBetravelerBtn.setOnClickListener { onBeTravellerClicked(ad) }
        }
    }
}