package com.bldj.project.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bldj.project.R
import data.ConstantValues

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
        val btn = inflaterThis.findViewById<Button>(R.id.ready_bttn)
        btn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace((view?.parent as View).id, TripsFragment(), "LoginSuccess")
                .commit()
        }
        val user = ConstantValues.auth!!.currentUser
        if (!ConstantValues.alreadyCreated)
            user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Email sent to ${user.email}", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to send email ${user.email}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
//                if (user.isEmailVerified) {
//                }
                }
        else {
            val txt = inflaterThis.findViewById<TextView>(R.id.registrationConfirmText)
            txt.text = "Вы уже зарегистрированы"
        }
    }
}