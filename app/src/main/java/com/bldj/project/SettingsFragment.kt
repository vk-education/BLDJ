package com.bldj.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import data.IBackButton

class SettingsFragment : Fragment(), IBackButton {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_layout, container, false)
    }

    override fun onBackPressed(): Boolean {
        TODO("Not yet implemented")
    }
}

//    override fun onBackPressed(): Boolean {
//         val fm = parentFragmentManager;
//        var backPressedListener: IBackButton? = null
//        for (Fragment fragment: fm.getFragments()) {
//            if (this instanceof  OnBackPressedListener) {
//                backPressedListener = (OnBackPressedListener) fragment;
//                break;
//            }
//        }
//
//        if (backPressedListener != null) {
//            backPressedListener.onBackPressed();
//        } else {
//            super.onBackPressed();
//        }
//    }
//    }
}