package com.bldj.project.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bldj.project.R
import com.bldj.project.adapters.AdAdapter
import com.bldj.project.databinding.FragmentMyTripsBinding
import com.bldj.project.listeners.IBeTraveller
import com.bldj.project.listeners.IGetAdvertInfo
import com.bldj.project.modelfactories.MyTripsModelFactory
import com.bldj.project.viewmodels.MyTripsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyTripsFragment : Fragment() {
    private lateinit var adAdapter: AdAdapter
    private lateinit var myTripsBinding: FragmentMyTripsBinding
    private var beTravelerListener: IBeTraveller? = null
    private var getInfoListener: IGetAdvertInfo? = null
    private val viewModel: MyTripsViewModel by viewModels { MyTripsModelFactory() }


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
        viewModel.trips.observe(viewLifecycleOwner, { x -> adAdapter.adsProperty = x })
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
        adAdapter.getInfoFuncProperty = { a -> getInfoListener?.onGetAdvertInfoClicked(a) }
        adAdapter.adsProperty = arrayListOf()
        myTripsBinding.apply {
            myAdsList.adapter = adAdapter
            invalidateAll()
        }
        initialize()
        val navigationBar = activity?.findViewById<BottomNavigationView>(R.id.nav_bar)
        navigationBar!!.visibility = View.VISIBLE
        return myTripsBinding.root
    }

    override fun onDetach() {
        beTravelerListener = null
        getInfoListener = null
//        if(BottomSheetInfoAds.deleted){
//            listAds.clear()
//            adAdapter.notifyDataSetChanged()
//        }
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        initialize()
    }

}