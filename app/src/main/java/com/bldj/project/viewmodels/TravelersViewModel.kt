package com.bldj.project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import data.ConstantValues
import data.User
import kotlinx.coroutines.launch

class TravelersViewModel : ViewModel() {
    private var usersDbRef: DatabaseReference? = null
    private var usersChildEventListener: ChildEventListener? = null
    private val listUsers = arrayListOf<User>()
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        if (ConstantValues.user?.myAdvert != null) {
            usersDbRef = FirebaseDatabase.getInstance().reference
                .child(ConstantValues.ADVERTS_DB_REFERENCE)
                .child("${ConstantValues.MY_ADVERT?.from}-${ConstantValues.MY_ADVERT?.to}")
                .child("users")
        }
        loadTravelers()
    }

    private fun loadTravelers() {
        viewModelScope.launch {
            val myAd = ConstantValues.user?.myAdvert
            if (myAd != null) {
                usersChildEventListener = object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val user = snapshot.value   //listOf(hashMap())
                        val mapUser = user as HashMap<*, *>
                        val newUser = User(
                            mapUser["email"] as String,
                            mapUser["name"] as String,
                            mapUser["group"] as String
                        )
                        newUser.isTraveller = mapUser["traveller"] as Boolean

                        if (!listUsers.contains(newUser)) {
                            listUsers.add(newUser)
                            _users.value = listUsers
                        }
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        val user = snapshot.value   //hashMap()
                        val mapUser = user as HashMap<*, *>

                        val deletedUser = User(
                            mapUser["email"] as String,
                            mapUser["name"] as String,
                            mapUser["group"] as String
                        )
                        deletedUser.isTraveller = mapUser["traveller"] as Boolean
                        val index = findIndex(deletedUser)
                        listUsers.removeAt(index)
                        _users.value = listUsers
                    }

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onCancelled(error: DatabaseError) {}
                }
                usersDbRef?.addChildEventListener(usersChildEventListener!!)
            }
        }
    }

    /**
     * Method finds the index of <code>deletedUser</code> in <code>users</code>.
     */
    private fun findIndex(deletedUser: User): Int {
        var deleteIndex: Int = -1
        for (i in 0..listUsers.size) {
            if (listUsers[i] == deletedUser) {
                deleteIndex = i
                break
            }
        }
        return deleteIndex
    }
}