package com.bldj.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bldj.project.databinding.FragmentBottomInfoAdsBinding
import com.bldj.project.databinding.FragmentBottomInfoTripBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import data.Advert
import data.ConstantValues
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetInfoTripFragment : BottomSheetDialogFragment() {

    private lateinit var infoHistBinding: FragmentBottomInfoTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        infoHistBinding = FragmentBottomInfoTripBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return infoHistBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ads =
            arguments?.getSerializable(BottomSheetInfoTripFragment.PARAM_AD) as? Advert ?: return

        val sdfHours = SimpleDateFormat("HH:mm", Locale.getDefault())
        val sdfDay = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = sdfDay.format(Date())

        infoHistBinding.from.text = ads.from
        infoHistBinding.to.text = ads.to
        infoHistBinding.cost.text = ads.price.toString() + "₽"

        infoHistBinding.notes.text = ads.notes
        infoHistBinding.time.text = "ехали в ${ads.departTime}"
//            if (sdfDay.format(ads.date).equals(currentDate))
//                "сегодня в ${sdfHours.format(ads.date)}"
//            else
//                "${sdfDay.format(ads.date)} в ${sdfHours.format(ads.date)}"
    }

    companion object {
        private const val PARAM_AD = "hist_id_param"
        fun newInstance(ad: Advert) = BottomSheetInfoTripFragment().also {
            val arg = bundleOf(PARAM_AD to ad)
            it.arguments = arg
        }

        var deleted: Boolean = false
    }
}