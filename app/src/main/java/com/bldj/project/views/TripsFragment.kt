package com.bldj.project.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bldj.project.R
import com.bldj.project.adapters.ViewPagerAdapter
import com.bldj.project.databinding.FragmentTripsBinding
import com.bldj.project.views.HistoryFragment
import com.google.android.material.tabs.TabLayoutMediator


class TripsFragment : Fragment() {
    private lateinit var tripsFragmentBinding: FragmentTripsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tripsFragmentBinding = FragmentTripsBinding.inflate(inflater, container, false)
        tripsFragmentBinding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(
            tripsFragmentBinding.tabLayout,
            tripsFragmentBinding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.my_poezdki)
                1 -> tab.text = getString(R.string.travelers_str)
            }
        }.attach()
//        val twelveDp = TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP, 9f,
//            tripsFragmentBinding.currentText.resources.displayMetrics
//        )
//        tripsFragmentBinding.currentText.textSize = twelveDp
        //tripsFragmentBinding.currentText.textSize = R.dimen._20ssp.toFloat()
        tripsFragmentBinding.historyText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(
                    (tripsFragmentBinding.root.parent/*currentTrips.parent*/ as View).id,
                    HistoryFragment()
                )
                .addToBackStack(null).commit()
        }
//        tripsFragmentBinding.historyText.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .add((tripsFragmentBinding.currentLayout.parent as View).id, HistoryFragment())
//                .addToBackStack(null).commit()
//        }
//        tripsFragmentBinding.currentText.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace((tripsFragmentBinding.currentLayout.parent as View).id, TripsFragment())
//                .addToBackStack(null).commit()
//        }
        return tripsFragmentBinding.root

    }

}