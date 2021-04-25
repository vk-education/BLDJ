package com.bldj.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * Фрагмент окна создания объявления.
 */
class CreateFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        val publishBtn = view.findViewById<Button>(R.id.create_ad)
        publishBtn?.setOnClickListener {
            val bottomSheet = BottomSheetCreateFragment()
            bottomSheet.show(requireFragmentManager(), "TAG")
        }

        // Inflate the layout for this fragment
        return view
    }
}