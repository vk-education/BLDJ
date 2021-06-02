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
        if (ads.owner != ConstantValues.user!!.id) {
            infoAdsBinding.confirmAd.visibility = View.GONE
            infoAdsBinding.deleteAd.visibility = View.GONE
        } else {
            infoAdsBinding.confirmAd.visibility = View.VISIBLE
            infoAdsBinding.deleteAd.visibility = View.VISIBLE
            infoAdsBinding.confirmAd.setOnClickListener {
                //Уехали
                ConstantValues.database?.reference?.child(ConstantValues.HISTORY_DB_REFERENCE)
                    ?.child("${ads.from}-${ads.to}")
                    ?.setValue(ads)
                val advRef =
                    ConstantValues.database?.reference?.child(ConstantValues.ADVERTS_DB_REFERENCE)
                advRef?.child("${ads.from}-${ads.to}")
                    ?.removeValue()
            }
            infoAdsBinding.deleteAd.setOnClickListener {
                val advRef =
                    ConstantValues.database?.reference?.child(ConstantValues.ADVERTS_DB_REFERENCE)
                advRef?.child("${ads.from}-${ads.to}")
                    ?.removeValue()//child(ConstantValues.USER_DB_REFERENCE)
                //?.setValue(ads.users)
                ConstantValues.user!!.myAdvert = Advert()
                ConstantValues.user!!.isTraveller = false

                val usersDbRef =
                    ConstantValues.database?.reference?.child(ConstantValues.USER_DB_REFERENCE)
                usersDbRef!!.child(ConstantValues.user!!.email.replace(".", ""))
                    .setValue(ConstantValues.user!!)
                deleted = true
            }
        }
        infoAdsBinding.from.text = ads.from
        infoAdsBinding.to.text = ads.to
        infoAdsBinding.cost.text = ads.price.toString() + "₽"
        infoAdsBinding.freeplaces.text = (ads.places - ads.users.size - 1).toString()
        infoAdsBinding.placesBar.numStars = ads.places
        val value: Int = ads.places - (ads.places - ads.users.size) + 1
        infoAdsBinding.placesBar.rating = value.toFloat()
        Log.i("STRANGEVAL", "${ads.places} ${ads.users.size}");

        infoAdsBinding.notes.text = ads.notes
        infoAdsBinding.time.text = "едем в ${ads.departTime}"
//            if (sdfDay.format(ads.date).equals(currentDate))
//                "сегодня в ${sdfHours.format(ads.date)}"
//            else
//                "${sdfDay.format(ads.date)} в ${sdfHours.format(ads.date)}"
    }

    companion object {
        private const val PARAM_AD = "ad_id_param"
        fun newInstance(ad: Advert) = BottomSheetInfoAds().also {
            val arg = bundleOf(PARAM_AD to ad)
            it.arguments = arg
        }

        var deleted: Boolean = false
    }
}