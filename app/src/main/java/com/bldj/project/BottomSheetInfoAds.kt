package com.bldj.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bldj.project.databinding.FragmentBottomInfoAdsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import data.Advert
import data.ConstantValues
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetInfoAds : BottomSheetDialogFragment() {

    private lateinit var infoAdsBinding: FragmentBottomInfoAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        infoAdsBinding = FragmentBottomInfoAdsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return infoAdsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ads = arguments?.getSerializable(PARAM_AD) as? Advert ?: return

        val sdfHours = SimpleDateFormat("HH:mm", Locale.getDefault())
        val sdfDay = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = sdfDay.format(Date())
        if(ads.owner!=ConstantValues.user!!.id){
            infoAdsBinding.confirmAd.visibility = View.GONE
            infoAdsBinding.deleteAd.visibility = View.GONE
        }else{
            infoAdsBinding.confirmAd.setOnClickListener {

            }
            infoAdsBinding.deleteAd.setOnClickListener {

            }
        }
        infoAdsBinding.from.text = ads.from
        infoAdsBinding.to.text = ads.to
        infoAdsBinding.cost.text = ads.price.toString() + "₽"
        infoAdsBinding.freeplaces.text = (ads.places - ads.users.size).toString()
        infoAdsBinding.placesBar.numStars = ads.places
        val value: Int = ads.places - (ads.places - ads.users.size)
        infoAdsBinding.placesBar.rating = value.toFloat()
        Log.i("STRANGEVAL", "${ads.places} ${ads.users.size}");

        infoAdsBinding.notes.text = ads.notes
        infoAdsBinding.time.text =
            if (sdfDay.format(ads.date).equals(currentDate))
                "сегодня в ${sdfHours.format(ads.date)}"
            else
                "${sdfDay.format(ads.date)} в ${sdfHours.format(ads.date)}"


    }

    companion object {
        private const val PARAM_AD = "ad_id_param"
        fun newInstance(ad: Advert) = BottomSheetInfoAds().also {
            val arg = bundleOf(PARAM_AD to ad)
            it.arguments = arg
        }
    }
}