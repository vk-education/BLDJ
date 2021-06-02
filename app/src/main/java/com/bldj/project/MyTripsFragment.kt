package com.bldj.project

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bldj.project.adapters.AdAdapter
import com.bldj.project.databinding.FragmentMyTripsBinding
import com.bldj.project.listeners.IBeTraveller
import com.bldj.project.listeners.IGetAdvertInfo
import com.google.firebase.database.*
import data.Advert
import data.ConstantValues


class MyTripsFragment : Fragment() {


    private var usersDbRef: DatabaseReference? = null
    private var listAds: ArrayList<Advert> = ArrayList()
    private var usersChildEventListener: ChildEventListener? = null
    private lateinit var adAdapter: AdAdapter
    private lateinit var myTripsBinding: FragmentMyTripsBinding
    private var listener: IBeTraveller? = null
    private var beTravelerListener: IBeTraveller? = null
    private var getInfoListener: IGetAdvertInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IBeTraveller) {
            beTravelerListener = context
        }
        if (context is IGetAdvertInfo) {
            getInfoListener = context
        }
    }

    private fun initialize() {
        usersDbRef =
            FirebaseDatabase.getInstance().reference.child(
                ConstantValues.ADVERTS_DB_REFERENCE
            )
        usersChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val ad: Advert = snapshot.getValue(Advert::class.java)!!
                if(ad.owner == ConstantValues.user!!.id)
                    listAds.add(ad)
//                var lst = arrayListOf<String>()
//                for (item in ad.users)
//                    lst.add(item.email)
//                if (lst.contains(ConstantValues.user!!.email)) {
//                    listAds.add(ad)
//                }
                adAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        usersDbRef?.addChildEventListener(usersChildEventListener!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myTripsBinding = FragmentMyTripsBinding.inflate(inflater, container, false)
        myTripsBinding.myAdsList.layoutManager = LinearLayoutManager(context)
        myTripsBinding.myAdsList.setHasFixedSize(true)
        adAdapter = AdAdapter { ad -> beTravelerListener?.onBeTravellerClicked(ad) }
        adAdapter.getInfoFuncProperty = { a -> getInfoListener?.onGetAdvertInfoClicked(a)}
        adAdapter.adsProperty = listAds
        myTripsBinding.apply {
            myAdsList.adapter = adAdapter
            invalidateAll()
        }
        initialize()
        return myTripsBinding.root
    }

    override fun onDetach() {
        beTravelerListener = null
        getInfoListener = null
        super.onDetach()
    }

}