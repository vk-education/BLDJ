package com.bldj.project

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.adapters.HistoryAdapter
import com.bldj.project.databinding.FragmentHistoryBinding
import com.bldj.project.listeners.IBeTraveller
import com.bldj.project.listeners.IGetAdvertInfo
import com.bldj.project.listeners.IGetHistoryInfo
import com.google.firebase.database.*
import data.Advert
import data.ConstantValues

/**
 * Фрфгмент истории поездок - часть главной страницы
 */
class HistoryFragment : Fragment() {

    private lateinit var historyFragmentBinding: FragmentHistoryBinding
    private var historyDbRef: DatabaseReference? = null
    private var beTravelerListener: IBeTraveller? = null
    private lateinit var historyAdapter: HistoryAdapter
    private var usersChildEventListener: ChildEventListener? = null
    private var getInfoListener: IGetHistoryInfo? = null
    private lateinit var histAds: ArrayList<Advert>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IGetHistoryInfo) {
            getInfoListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        histAds = arrayListOf()
        historyDbRef =
            FirebaseDatabase.getInstance().reference.child(
                ConstantValues.HISTORY_DB_REFERENCE
            )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyFragmentBinding = FragmentHistoryBinding.inflate(inflater, container, false)
        historyFragmentBinding.back.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
        historyFragmentBinding.currentText.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStackImmediate();
            }
//            parentFragmentManager.beginTransaction()
//                .replace((historyFragmentBinding.root.parent as View).id, TripsFragment())
//                .commit()
        }
        return historyFragmentBinding.root
    }

    override fun onStart() {
        super.onStart()
        historyFragmentBinding.rvMovies.layoutManager = LinearLayoutManager(context)
        historyFragmentBinding.rvMovies.setHasFixedSize(true)

        historyAdapter = HistoryAdapter { ad -> getInfoListener?.onGetHistoryInfoClicked(ad) }
        historyAdapter.adsProperty = histAds

        historyFragmentBinding.apply {
            historyFragmentBinding.rvMovies.adapter = historyAdapter
            invalidateAll()
        }

        historyFragmentBinding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!historyFragmentBinding.rvMovies.canScrollVertically(1)) {
                    updateHistory()
                }
            }
        })
        updateHistory()
    }

    private fun updateHistory() {
        usersChildEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                runBlocking {
//                    launch {

                        val ad: Advert = snapshot.getValue(Advert::class.java)!!
                Log.i("ADVHIST", ad.toString())
                        if (!histAds.contains(ad)) {
                            histAds.add(ad)
                            Log.i("ads", "dobavil")
                        }
//                    }
//                }
                historyAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {
                val deletedAdvert = snapshot.getValue(Advert::class.java)!!
                val index = findIndex(deletedAdvert)
                //listAds.removeAt(index)
                historyAdapter.notifyItemRemoved(index)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        historyDbRef?.addChildEventListener(usersChildEventListener!!)
    }

    override fun onDetach() {
        beTravelerListener = null
        getInfoListener = null
        super.onDetach()
    }

    /**
     * Method finds the index of <code>deletedAdvert</code> in <code>listAds</code>.
     */
    private fun findIndex(deletedAdvert: Advert): Int {
        var deleteIndex: Int = -1
        for (i in 0 until histAds.size) {
            if (histAds[i] == deletedAdvert) {
                deleteIndex = i
                break
            }
        }
        return deleteIndex
    }
}