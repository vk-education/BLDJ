package com.bldj.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.InvalidParameterException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            moveToFragment(AdsFragment())

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        //Set the click listener on nav bar.
        navigationBar.setOnNavigationItemSelectedListener {
            val frag = when (it.itemId) {
                R.id.home -> AdsFragment()
                R.id.add -> CreateFragment()
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