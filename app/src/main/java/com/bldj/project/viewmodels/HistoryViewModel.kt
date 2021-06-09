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

class HistoryViewModel : ViewModel() {
    private val _hist = MutableLiveData<List<Advert>>()
    private val histAds = arrayListOf<Advert>()
    private var historyDbRef: DatabaseReference? = null
    private var usersChildEventListener: ChildEventListener? = null
    val hist: LiveData<List<Advert>> get() = _hist

    init {
        historyDbRef =
            ConstantValues.database?.reference?.child(
                ConstantValues.HISTORY_DB_REFERENCE
            )
        loadHistories()
    }

    private fun loadHistories() {
        viewModelScope.launch {
            usersChildEventListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val ad: Advert = snapshot.getValue(Advert::class.java)!!
                    Log.i("ADVHIST", ad.toString())
                    if (!histAds.contains(ad)) {
                        histAds.add(ad)
                        _hist.value = histAds
                        Log.i("ads", "dobavil")
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val deletedAdvert = snapshot.getValue(Advert::class.java)!!
                    val index = findIndex(deletedAdvert, histAds)
                    histAds.removeAt(index)
                    _hist.value = histAds
                    Log.i("USERDELETETCHO", ConstantValues.user?.isTraveller.toString())
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            }
            historyDbRef?.addChildEventListener(usersChildEventListener!!)
        }
    }

    /**
     * Method finds the index of <code>deletedAdvert</code> in <code>listAds</code>.
     */
    private fun findIndex(deletedAdvert: Advert, listAds: List<Advert>): Int {
        var deleteIndex: Int = -1
        for (i in listAds.indices) {
            if (listAds[i] == deletedAdvert) {
                deleteIndex = i
                break
            }
        }
        return deleteIndex
    }
}