package com.bldj.project.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bldj.project.adapters.HistoryAdapter
import com.bldj.project.databinding.FragmentHistoryBinding
import com.bldj.project.listeners.IBeTraveller
import com.bldj.project.listeners.IGetHistoryInfo
import com.bldj.project.modelfactories.HistoryModelFactory
import com.bldj.project.viewmodels.HistoryViewModel
import com.google.firebase.database.*
import data.Advert
import data.ConstantValues

/**
 * Фрфгмент истории поездок - часть главной страницы
 */
class HistoryFragment : Fragment() {

    private lateinit var historyFragmentBinding: FragmentHistoryBinding
    private var beTravelerListener: IBeTraveller? = null
    private lateinit var historyAdapter: HistoryAdapter
    private var getInfoListener: IGetHistoryInfo? = null

    private val viewModel: HistoryViewModel by viewModels { HistoryModelFactory() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IGetHistoryInfo) {
            getInfoListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyFragmentBinding = FragmentHistoryBinding.inflate(inflater, container, false)

        historyFragmentBinding.currentText.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStackImmediate();
            }
        }
        return historyFragmentBinding.root
    }

    override fun onStart() {
        super.onStart()
        historyFragmentBinding.rvMovies.layoutManager = LinearLayoutManager(context)
        historyFragmentBinding.rvMovies.setHasFixedSize(true)

        historyAdapter = HistoryAdapter { ad -> getInfoListener?.onGetHistoryInfoClicked(ad) }
        historyAdapter.adsProperty = arrayListOf()
        updateHistory()
        historyFragmentBinding.apply {
            historyFragmentBinding.rvMovies.adapter = historyAdapter
            invalidateAll()
        }

        historyFragmentBinding.rvMovies.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
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
        viewModel.hist.observe(viewLifecycleOwner, { x -> historyAdapter.adsProperty = x })
    }

    override fun onDetach() {
        beTravelerListener = null
        getInfoListener = null
        super.onDetach()
    }
}