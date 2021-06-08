package com.bldj.project.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bldj.project.adapters.UsersAdapter
import com.bldj.project.databinding.FragmentTravelersBinding
import com.bldj.project.modelfactories.TravelersViewModelFactory
import com.bldj.project.viewmodels.TravelersViewModel

/**
 * Страница с попутчиками
 */
class TravelersFragment : Fragment() {
    private lateinit var travelersBinding: FragmentTravelersBinding
    private lateinit var usersAdapter: UsersAdapter
    private val viewModel: TravelersViewModel by viewModels { TravelersViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(ConstantValues.user?.myAdvert!=null){
//            usersDbRef = FirebaseDatabase.getInstance().reference
//                .child(ConstantValues.ADVERTS_DB_REFERENCE)
//                .child("${ConstantValues.user!!.myAdvert.from}-${ConstantValues.user!!.myAdvert.to}")
//                .child("users")
//        }
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
        usersAdapter = UsersAdapter(arrayListOf())
        travelersBinding.apply {
            travelersBinding.usersList.adapter = usersAdapter
            invalidateAll()
        }

        updateData()
    }

    private fun updateData() {
        viewModel.users.observe(viewLifecycleOwner, { x -> usersAdapter.users = x })
    }
}