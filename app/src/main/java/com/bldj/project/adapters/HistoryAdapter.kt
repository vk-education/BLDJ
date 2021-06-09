package com.bldj.project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.R
import com.bldj.project.databinding.HistoryViewHolderBinding
import data.Advert
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(val onGetInfoClicked: (ad: Advert) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    val sdfHours = SimpleDateFormat("HH:mm", Locale.getDefault())
    val sdfDay = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private lateinit var ads: List<Advert>

    //Property for ads List field.
    var adsProperty: List<Advert>
        get() = ads
        set(value) {
            ads = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)
        val adBinding: HistoryViewHolderBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.history_view_holder, parent, false)
        return HistoryViewHolder(adBinding)
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindAd(ads[position])
    }

    private var layoutInflater: LayoutInflater? = null

    inner class HistoryViewHolder(adbinding_: HistoryViewHolderBinding) :
        RecyclerView.ViewHolder(adbinding_.root) {
        var adBinding: HistoryViewHolderBinding = adbinding_
        fun bindAd(ad: Advert) {

            adBinding.ad = ad
            adBinding.executePendingBindings()

            adBinding.adInfoBtn.setOnClickListener { onGetInfoClicked(ad) }

            val currentDate = sdfDay.format(Date())
            adBinding.adTime.text =
                if (sdfDay.format(ad.date).equals(currentDate))
                    "сегодня в ${sdfHours.format(ad.date)}"
                else
                    "${sdfDay.format(ad.date)} в ${sdfHours.format(ad.date)}"
//            adBinding.adInfoBtn.setOnClickListener{
//                Toast.makeText(adBinding.root.context,"",Toast.LENGTH_LONG).show()
//            }
        }
    }
}