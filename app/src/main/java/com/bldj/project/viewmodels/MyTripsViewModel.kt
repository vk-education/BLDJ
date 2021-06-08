package com.bldj.project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bldj.project.views.BottomSheetInfoAds
import com.google.firebase.database.*
import data.Advert
import data.ConstantValues

class MyTripsViewModel : ViewModel() {
    private val _trips = MutableLiveData<List<Advert>>()
    private var usersDbRef: DatabaseReference? = null
    private var usersChildEventListener: ChildEventListener? = null
    private val tripsList = arrayListOf<Advert>()
    val trips: LiveData<List<Advert>> get() = _trips

    init {
        usersDbRef =
            ConstantValues.database?.reference?.child(
                ConstantValues.ADVERTS_DB_REFERENCE
            )
        loadMyTrips()
    }

    private fun loadMyTrips() {
        usersDbRef =
            FirebaseDatabase.getInstance().reference.child(
                ConstantValues.ADVERTS_DB_REFERENCE
            )
        usersChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val ad: Advert = snapshot.getValue(Advert::class.java)!!
                if (ad.owner == ConstantValues.user!!.id && !tripsList.contains(ad)) {
                    tripsList.add(ad)
                    _trips.value = tripsList
                    ConstantValues.MY_ADVERT = ad
                }
                for (item in ad.users)
                    if (item.id == ConstantValues.user!!.id && !tripsList.contains(ad)) {
                        tripsList.add(ad)
                        ConstantValues.MY_ADVERT = ad
                        _trips.value = tripsList
                    }
//                var lst = arrayListOf<String>()
//                for (item in ad.users)
//                    lst.add(item.email)
//                if (lst.contains(ConstantValues.user!!.email)) {
//                    listAds.add(ad)
//                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {
                tripsList.clear()
                _trips.value = tripsList
                BottomSheetInfoAds.deleted = false
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        usersDbRef?.addChildEventListener(usersChildEventListener!!)
    }

}