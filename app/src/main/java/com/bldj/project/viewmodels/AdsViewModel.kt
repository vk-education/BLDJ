package com.bldj.project.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import data.Advert
import data.ConstantValues
import kotlinx.coroutines.launch

class AdsViewModel : ViewModel() {
    private val _ads = MutableLiveData<List<Advert>>()
    private var usersDbRef: DatabaseReference? = null
    private var usersChildEventListener: ChildEventListener? = null
    val ads: LiveData<List<Advert>> get() = _ads
    var listAds: ArrayList<Advert> = arrayListOf()

    init {
        usersDbRef =
            ConstantValues.database?.reference?.child(
                ConstantValues.ADVERTS_DB_REFERENCE
            )
        loadAds()
    }

    private fun loadAds() {
        viewModelScope.launch {
            usersChildEventListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val ad: Advert = snapshot.getValue(Advert::class.java)!!
                    if (!listAds.contains(ad)) {
                        listAds.add(ad)
                        _ads.value = listAds
                        Log.i("ads", _ads.value.toString())
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val deletedAdvert = snapshot.getValue(Advert::class.java)!!
                    val index = findIndex(deletedAdvert)
                    if (listAds[index].users.contains(ConstantValues.user)) {
                        ConstantValues.user?.isTraveller = false
                    }
                    listAds.removeAt(index)
                    _ads.value = listAds
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            }
            usersDbRef?.addChildEventListener(usersChildEventListener!!)
        }
    }

    /**
     * Method finds the index of <code>deletedAdvert</code> in <code>listAds</code>.
     */
    private fun findIndex(deletedAdvert: Advert): Int {
        var deleteIndex: Int = -1
        for (i in 0 until listAds.size) {
            Log.i("ShowingAsViewModellllss", "${listAds[i]}\nтакже${deletedAdvert}")
            if (listAds[i] == deletedAdvert) {
                deleteIndex = i
                break
            }
        }
        return deleteIndex
    }
}