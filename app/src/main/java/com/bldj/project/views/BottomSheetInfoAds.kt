package com.bldj.project.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bldj.project.R
import com.bldj.project.databinding.FragmentBottomInfoAdsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DatabaseReference
import data.Advert
import data.ConstantValues
import data.User
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetInfoAds : BottomSheetDialogFragment() {

    private lateinit var infoAdsBinding: FragmentBottomInfoAdsBinding
    private lateinit var usersDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersDbRef = ConstantValues.database?.reference?.child(ConstantValues.USER_DB_REFERENCE)!!
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
            //infoAdsBinding.deleteAd.visibility = View.VISIBLE
            infoAdsBinding.deleteAd.background = context?.getDrawable(R.drawable.ic_exit)
            infoAdsBinding.deleteAd.setOnClickListener {
                val advRef =
                ConstantValues.database?.reference?.child(ConstantValues.ADVERTS_DB_REFERENCE)
                var usr = User()
                var index = 0
                for(users in ads.users){
                    if(users.id == ConstantValues.user!!.id){
                        usr = users
                        usr.isTraveller = false
                        break
                    }
                    index++;
                }
                ads.users.removeAt(index)
                advRef?.child("${ads.from}-${ads.to}")?.setValue(ads)

                ConstantValues.user!!.isTraveller = false
                usersDbRef.child(ConstantValues.user!!.email.replace(".", ""))
                    .setValue(ConstantValues.user!!)
            }
        } else {
            infoAdsBinding.confirmAd.visibility = View.VISIBLE
            infoAdsBinding.deleteAd.visibility = View.VISIBLE
            infoAdsBinding.confirmAd.setOnClickListener {
                //Уехали
                setUsersNotTravellers(ads, usersDbRef)
                ConstantValues.database?.reference?.child(ConstantValues.HISTORY_DB_REFERENCE)
                    ?.child("${ads.from}-${ads.to}")
                    ?.setValue(ads)
            }

            infoAdsBinding.deleteAd.setOnClickListener {
                //child(ConstantValues.USER_DB_REFERENCE)
                //?.setValue(ads.users)

                setUsersNotTravellers(ads, usersDbRef)
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
        infoAdsBinding.time.text = getString(R.string.going_at, ads.departTime)
//            if (sdfDay.format(ads.date).equals(currentDate))
//                "сегодня в ${sdfHours.format(ads.date)}"
//            else
//                "${sdfDay.format(ads.date)} в ${sdfHours.format(ads.date)}"
    }

    /**
     * Sets the value isTraveller to false in DB for all users in trip.
     */
    private fun setUsersNotTravellers(ads: Advert, usersDbRef: DatabaseReference) {
        val advRef =
            ConstantValues.database?.reference?.child(ConstantValues.ADVERTS_DB_REFERENCE)
        advRef?.child("${ads.from}-${ads.to}")
            ?.removeValue()

        ConstantValues.user!!.myAdvert = Advert()
        ConstantValues.user!!.isTraveller = false

        //Делаем юзеров не попутчиками
        usersDbRef.child(ConstantValues.user!!.email.replace(".", ""))
            .setValue(ConstantValues.user!!)

        for (us in ads.users) {
            us.isTraveller = false
            usersDbRef.child(us.email.replace(".", ""))
                .setValue(us)
        }
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