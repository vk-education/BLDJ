package com.bldj.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.security.InvalidParameterException
import java.time.Duration

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private var database: FirebaseDatabase? = null
    private var usersDbRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        Log.i("Authmail",auth?.currentUser?.email.toString())
        if (auth?.currentUser != null) {
            if (savedInstanceState == null)
                moveToFragment(AdsFragment())
        } else {
            if (savedInstanceState == null)
                moveToFragment(LoginFragment())
        }

//        if (savedInstanceState == null)
//            moveToFragment(AdsFragment())

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        //Set the click listener on nav bar.
        navigationBar.setOnNavigationItemSelectedListener {
            val frag = when (it.itemId) {
                R.id.home -> AdsFragment()
                R.id.add -> CreateFragment()
                R.id.user -> ProfileFragment()
                else -> throw InvalidParameterException()
            }
            moveToFragment(frag)
            true
        }
    }

    /**
     * Begins transaction to a selected on a navigation bar fragment.
     */
    private fun moveToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_wrapper, fragment)
            .commit()
    }


}