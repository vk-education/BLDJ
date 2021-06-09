package com.bldj.project.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bldj.project.views.MyTripsFragment
import com.bldj.project.views.TravelersFragment

class ViewPagerAdapter(fragmentManager: Fragment) : FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        Log.i("adapter", "ya tut bil")
        return when (position) {
            0 -> MyTripsFragment()
            1 -> TravelersFragment()
            else -> MyTripsFragment()
        }

    }
}

