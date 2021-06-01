package com.bldj.project

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.adapters.AdAdapter
import com.bldj.project.databinding.FragmentAdsBinding
import com.google.firebase.database.*
import data.Advert
import data.ConstantValues

/**
 * Фрагмент для окна объявлений.
 */
class AdsFragment : Fragment() {

    private var usersDbRef: DatabaseReference? = null
    private lateinit var listAds: ArrayList<Advert>
    private var usersChildEventListener: ChildEventListener? = null
    private lateinit var adAdapter: AdAdapter
    private lateinit var adsFragmentBinding: FragmentAdsBinding
    private var listener: IBeTraveller? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IBeTraveller) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        adsFragmentBinding = FragmentAdsBinding.inflate(inflater, container, false)
        return adsFragmentBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAds = ArrayList()

        usersDbRef =
            FirebaseDatabase.getInstance().reference.child(
                ConstantValues.ADVERTS_DB_REFERENCE
            )
    }


    override fun onStart() {
        super.onStart()
        adsFragmentBinding.rvMovies.layoutManager = LinearLayoutManager(context)
        adsFragmentBinding.rvMovies.setHasFixedSize(true)

        adAdapter = AdAdapter { ad -> listener?.onBeTravellerClicked(ad) }
        adAdapter.adsProperty = listAds

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
        usersChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val ad: Advert = snapshot.getValue(Advert::class.java)!!
                if (!listAds.contains(ad)) {
                    listAds.add(ad)
                    Log.i("ads", "dobavil")
                }
                adAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {
                val deletedAdvert = snapshot.getValue(Advert::class.java)!!
                val index = findIndex(deletedAdvert)
                listAds.removeAt(index)
                adAdapter.notifyItemRemoved(index)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        usersDbRef?.addChildEventListener(usersChildEventListener!!)
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    /**
     * Method finds the index of <code>deletedAdvert</code> in <code>listAds</code>.
     */
    private fun findIndex(deletedAdvert: Advert): Int {
        var deleteIndex: Int = -1
        for (i in 0..listAds.size) {
            if (listAds[i] == deletedAdvert) {
                deleteIndex = i
                break
            }
        }
        return deleteIndex
    }
}

/**
 * Interface for adding listener to beTraveller button.
 */
interface IBeTraveller {
    fun onBeTravellerClicked(ad: Advert)
}