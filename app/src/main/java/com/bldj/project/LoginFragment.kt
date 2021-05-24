package com.bldj.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data.User
import kotlin.math.log

class LoginFragment : Fragment() {

    private var auth: FirebaseAuth? = null
    private var database: FirebaseDatabase? = null
    private var usersDbRef: DatabaseReference? = null

    lateinit var inflaterThis: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        usersDbRef = database?.reference?.child("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflaterThis =
            LayoutInflater.from(this.context).inflate(R.layout.login_layout, container, false)

        val loginET: EditText = inflaterThis.findViewById(R.id.login_edit)
        inflaterThis.findViewById<Button>(R.id.go_bttn).setOnClickListener { onLogin(loginET) }
        // Inflate the layout for this fragment
        return inflaterThis
    }

    private fun onLogin(loginET: EditText) {
        val password = "parol123"
        val login = loginET.text.toString()
        Log.i("LOGINPAROL", login)
        auth?.createUserWithEmailAndPassword(login, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth", "createUserWithEmail:success")
                    var user = User(login, password)
                    parentFragmentManager.beginTransaction()
                        .replace((view?.parent as View).id, AccessCodeFragment(), "LoginSuccess")
                        .commit()

                } else {
                    Log.i("authq", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this.context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}