package com.bldj.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bldj.project.adapters.UsersAdapter
import com.bldj.project.databinding.FragmentTravelersBinding
import com.google.firebase.database.*
import data.ConstantValues
import data.User

/**
 * Страница с попутчиками
 */
class TravelersFragment : Fragment() {

    private lateinit var usersDbRef: DatabaseReference
    private lateinit var travelersBinding: FragmentTravelersBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var users: MutableList<User>
    private lateinit var usersChildEventListener: ChildEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        users = mutableListOf()
//        if (ConstantValues.MY_ADVERT != null) {
//            usersDbRef = FirebaseDatabase.getInstance().reference
//                .child(ConstantValues.ADVERTS_DB_REFERENCE)
//                .child("${ConstantValues.MY_ADVERT?.from}-${ConstantValues.MY_ADVERT?.to}")
//                .child("users")
//        }
        if(ConstantValues.user?.myAdvert!=null){
            usersDbRef = FirebaseDatabase.getInstance().reference
                .child(ConstantValues.ADVERTS_DB_REFERENCE)
                .child("${ConstantValues.user!!.myAdvert.from}-${ConstantValues.user!!.myAdvert.to}")
                .child("users")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        travelersBinding = FragmentTravelersBinding.inflate(inflater, container, false)
        return travelersBinding.root
    }

    override fun onStart() {
        super.onStart()
        travelersBinding.usersList.layoutManager = LinearLayoutManager(context)
        travelersBinding.usersList.setHasFixedSize(true)
        usersAdapter = UsersAdapter(users)
        travelersBinding.apply {
            travelersBinding.usersList.adapter = usersAdapter
            invalidateAll()
        }

        updateData()
    }

    private fun updateData() {
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

                    users.add(newUser)
                    usersAdapter.notifyDataSetChanged()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

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

                    users.removeAt(index)
                    usersAdapter.notifyItemRemoved(index)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            }
            usersDbRef.addChildEventListener(usersChildEventListener)
        }
    }

    /**
     * Method finds the index of <code>deletedUser</code> in <code>users</code>.
     */
    private fun findIndex(deletedUser: User): Int {
        var deleteIndex: Int = -1
        for (i in 0..users.size) {
            if (users[i] == deletedUser) {
                deleteIndex = i
                break
            }
        }
        return deleteIndex
    }
}