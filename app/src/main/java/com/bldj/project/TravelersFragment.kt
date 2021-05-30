package com.bldj.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.adapters.AdAdapter
import com.bldj.project.adapters.UsersAdapter
import com.bldj.project.databinding.FragmentTravelersBinding
import com.google.firebase.database.*
import data.Advert
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
    private lateinit var usersValueEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        users = mutableListOf()
        if (ConstantValues.MY_ADVERT != null) {
            usersDbRef = FirebaseDatabase.getInstance().reference
                .child(ConstantValues.ADVERTS_DB_REFERENCE)
                .child("${ConstantValues.MY_ADVERT?.from}-${ConstantValues.MY_ADVERT?.to}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        travelersBinding = FragmentTravelersBinding.inflate(inflater, container, false)
        travelersBinding.usersList.setHasFixedSize(true)
        usersAdapter = UsersAdapter(users)
        val myAd = ConstantValues.MY_ADVERT
        if (myAd != null) {
            usersValueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)!!

                        users.add(user)
                        Log.i("userTAGTtravelers", user.email)

                }

                override fun onCancelled(error: DatabaseError) {}
            }

            usersDbRef.addValueEventListener(usersValueEventListener)
        }

//        val dividerItemDecoration = DividerItemDecoration(travelersBinding.usersList.context, RecyclerView.VERTICAL)
//        travelersBinding.usersList.addItemDecoration(dividerItemDecoration)
        travelersBinding.usersList.layoutManager = LinearLayoutManager(context)
        travelersBinding.apply {
            travelersBinding.usersList.adapter = usersAdapter
            invalidateAll()
        }
        return travelersBinding.root
        //return inflater.inflate(R.layout.fragment_travelers, container, false)
    }


}