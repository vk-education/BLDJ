package com.bldj.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bldj.project.databinding.FragmentHistoryBinding

/**
 * Фрфгмент истории поездок - часть главной страницы
 */
class HistoryFragment : Fragment() {

    private lateinit var historyFragmentBinding: FragmentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


}