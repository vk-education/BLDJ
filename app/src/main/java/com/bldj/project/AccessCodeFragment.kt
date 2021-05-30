package com.bldj.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AccessCodeFragment : Fragment() {

    lateinit var inflaterThis: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflaterThis = inflater.inflate(R.layout.accesscode_layout, container, false)
        verifyEmail()

        // Inflate the layout for this fragment
        return inflaterThis
    }

    private fun verifyEmail() {
        val user = ConstantValues.auth!!.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Email sent to ${user.email}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to send email ${user.email}", Toast.LENGTH_LONG)
                        .show()
                }
//                if (user.isEmailVerified) {
                parentFragmentManager.beginTransaction()
                    .replace((view?.parent as View).id, TripsFragment(), "LoginSuccess")
                    .commit()
//                }
            }
    }
}