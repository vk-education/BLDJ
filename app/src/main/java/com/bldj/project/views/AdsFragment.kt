package com.bldj.project.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.adapters.AdAdapter
import com.bldj.project.databinding.FragmentAdsBinding
import com.bldj.project.listeners.IBeTraveller
import com.bldj.project.listeners.IGetAdvertInfo
import com.bldj.project.modelfactories.AdsModelFactory
import com.bldj.project.viewmodels.AdsViewModel
import com.google.firebase.database.*
import data.ConstantValues

/**
 * Фрагмент для окна объявлений.
 */
class AdsFragment : Fragment() {
    private lateinit var adAdapter: AdAdapter
    private lateinit var adsFragmentBinding: FragmentAdsBinding
    private var beTravelerListener: IBeTraveller? = null
    private var getInfoListener: IGetAdvertInfo? = null
    private val viewModel: AdsViewModel by viewModels { AdsModelFactory() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IBeTraveller) {
            beTravelerListener = context
        }
        if (context is IGetAdvertInfo) {
            getInfoListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("TUTLISTEN1", "IST")
        // Inflate the layout for this fragment
        adsFragmentBinding = FragmentAdsBinding.inflate(inflater, container, false)
        return adsFragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TUTLISTEN2", "IST")
    }


    override fun onStart() {
        super.onStart()
        adsFragmentBinding.rvMovies.layoutManager = LinearLayoutManager(context)
        adsFragmentBinding.rvMovies.setHasFixedSize(true)

        adAdapter = AdAdapter { ad -> beTravelerListener?.onBeTravellerClicked(ad) }
        adAdapter.getInfoFuncProperty = { a -> getInfoListener?.onGetAdvertInfoClicked(a) }
        adAdapter.adsProperty = arrayListOf()
        updateAds()
        adsFragmentBinding.apply {
            adsFragmentBinding.rvMovies.adapter = adAdapter
            invalidateAll()
        }

        adsFragmentBinding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!adsFragmentBinding.rvMovies.canScrollVertically(1)) {
                    updateAds()
                }
            }
        })
        updateAds()
    }

    private fun updateAds() {
        viewModel.ads.observe(
            viewLifecycleOwner,
            { x ->
                adAdapter.adsProperty = x
                Log.i("codemvv", x.toString())
            }
        )

//        usersChildEventListener = object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                runBlocking {
//                    launch {
//                        val ad: Advert = snapshot.getValue(Advert::class.java)!!
//                        if (!listAds.contains(ad)) {
//                            listAds.add(ad)
//                            Log.i("ads", "dobavil")
//                        }
//                    }
//                }
//                adAdapter.notifyDataSetChanged()
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                val deletedAdvert = snapshot.getValue(Advert::class.java)!!
//                val index = findIndex(deletedAdvert)
//                //listAds.removeAt(index)
//                adAdapter.notifyItemRemoved(index)
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
//            override fun onCancelled(error: DatabaseError) {}
//        }
//        usersDbRef?.addChildEventListener(usersChildEventListener!!)
    }

    override fun onDetach() {
        beTravelerListener = null
        getInfoListener = null
        super.onDetach()
    }
}