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

        val login: String = inflaterThis.findViewById<EditText>(R.id.login_edit).text.toString()
        inflaterThis.findViewById<Button>(R.id.go_bttn).setOnClickListener { onLogin(login) }
        // Inflate the layout for this fragment
        return inflaterThis
    }

    private fun onLogin(login: String) {
        val password = "parol123"

        auth?.createUserWithEmailAndPassword("noviyya@mail.ru", password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth", "createUserWithEmail:success")
                    var user = User(login, password)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.accesscode_lay, AccessCodeFragment())
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