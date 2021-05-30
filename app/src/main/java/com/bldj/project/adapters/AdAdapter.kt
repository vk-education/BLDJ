package com.bldj.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.R
import com.bldj.project.databinding.AdsViewHolderBinding
import data.Advert
import java.text.SimpleDateFormat
import java.util.*

class AdAdapter(val onBeTravellerClicked: (ad: Advert) -> Unit) :
    RecyclerView.Adapter<AdAdapter.AdViewHolder>() {

    val sdfHours = SimpleDateFormat("HH:mm", Locale.getDefault())
    val sdfDay = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
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

            val currentDate = sdfDay.format(Date())
            adBinding.adTime.text =
                if (sdfDay.format(ad.date).equals(currentDate))
                    "сегодня в ${sdfHours.format(ad.date)}"
                else
                    "${sdfDay.format(ad.date)} в ${sdfHours.format(ad.date)}"
        }
    }
}