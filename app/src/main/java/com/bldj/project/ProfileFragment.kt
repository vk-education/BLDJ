package com.bldj.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.add

class ProfileFragment : Fragment() {

    lateinit var inflaterThis: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflaterThis = inflater.inflate(R.layout.my_profile, container, false)

        val settings: Button = inflaterThis.findViewById(R.id.settings)
        val info: Button = inflaterThis.findViewById(R.id.info)

        settings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add((inflaterThis.parent as View).id, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        info.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add((inflaterThis.parent as View).id, InfoFragment())
                .addToBackStack(null)
                .commit()
        }

        // Inflate the layout for this fragment
        return inflaterThis
    }

}