package com.bldj.project.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bldj.project.R
import com.google.firebase.auth.FirebaseAuth
import data.ConstantValues

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
        val exit:Button = inflaterThis.findViewById(R.id.exit)
        val nameLastname: TextView = inflaterThis.findViewById(R.id.name_lastName)
        val email: TextView = inflaterThis.findViewById(R.id.email)

        nameLastname.text = ConstantValues.user!!.name
        email.text = ConstantValues.user!!.email

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
        exit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            parentFragmentManager.beginTransaction()
                .add((inflaterThis.parent as View).id, LoginFragment())
                .addToBackStack(null)
                .commit()
        }

        // Inflate the layout for this fragment
        return inflaterThis
    }

}