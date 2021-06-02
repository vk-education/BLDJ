package com.bldj.project

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bldj.project.listeners.IBeTraveller
import com.bldj.project.listeners.IGetAdvertInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import data.Advert
import data.ConstantValues
import data.IBackButton
import data.User
import kotlinx.coroutines.*
import java.security.InvalidParameterException

class MainActivity : AppCompatActivity(), IBeTraveller, IGetAdvertInfo {

    private var usersDbRef: DatabaseReference? = null
    private var usersChildEventListener: ChildEventListener? = null
    private var oldId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        ConstantValues.auth = Firebase.auth
        if (ConstantValues.auth?.currentUser != null) {
            if (ConstantValues.user == null) {
                ConstantValues.database = FirebaseDatabase.getInstance()
                usersDbRef =
                    ConstantValues.database?.reference?.child(ConstantValues.USER_DB_REFERENCE)
                usersChildEventListener = object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val user: User = snapshot.getValue(User::class.java)!!
                        if (user.id == FirebaseAuth.getInstance().currentUser.uid) {
                            ConstantValues.user = user
                        }
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {}
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onCancelled(error: DatabaseError) {}
                }
                usersDbRef?.addChildEventListener(usersChildEventListener as ChildEventListener)
            }
            moveToFragment(TripsFragment())
        } else {
            moveToFragment(LoginFragment())
        }
//        Log.i("Authmail", auth?.currentUser?.email.toString())
//        if (auth?.currentUser != null) {
//            if (savedInstanceState == null)
//                moveToFragment(AdsFragment())
//        } else {
//            if (savedInstanceState == null)
//                moveToFragment(LoginFragment())
//        }

//        if (savedInstanceState == null)
//            moveToFragment(AdsFragment())

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        //Set the click listener on nav bar.
        navigationBar.setOnNavigationItemSelectedListener {
            val frag = when (it.itemId) {
                R.id.home -> TripsFragment()
                R.id.adverts -> AdsFragment()
                R.id.add -> CreateFragment()
                R.id.user -> ProfileFragment()
                else -> throw InvalidParameterException()
            }
            if (oldId != -1 || oldId != it.itemId) {
                moveToFragment(frag)
                oldId = it.itemId
            }
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

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.activity_main_id)
        (fragment as? IBackButton)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }

    override fun onBeTravellerClicked(ad: Advert) {
        Log.i("onBeTravellerClicked", ad.from)
        if(ad == null){
            Log.i("check","ad === null")
        }else if(ad.users==null){
            Log.i("check","ad.users === null")
        }else if(ConstantValues.user == null){
            Log.i("check","user === null")
        }
        //Checking if user is already a traveler of the advert or other advert.
        if (ConstantValues.user?.isTraveller!!) {
            Toast.makeText(this, "Вы уже находитесь в другой поездке", Toast.LENGTH_LONG).show()
        } else if (ad.users.contains(ConstantValues.user!!) || ad.owner == ConstantValues.user!!.id) {
            Toast.makeText(this, "Вы уже попутчик", Toast.LENGTH_LONG).show()
        } else {
            ConstantValues.MY_ADVERT = ad
            ad.users.add(ConstantValues.user!!)
            runBlocking {
                beTraveller(ad)
            }
            Toast.makeText(this, "Поздравляю! Вы теперь попутчик.", Toast.LENGTH_LONG).show()
        }
        ConstantValues.MY_ADVERT = ad
    }

    /**
     * suspend fun for being a child
     */
    private suspend fun beTraveller(ad: Advert) = coroutineScope {
        launch {
            val advRef =
                ConstantValues.database?.reference?.child(ConstantValues.ADVERTS_DB_REFERENCE)
            advRef?.child("${ad.from}-${ad.to}")?.child(ConstantValues.USER_DB_REFERENCE)
                ?.setValue(ad.users)

            //ConstantValues.user!!.myAdvert = ad
            ConstantValues.user!!.isTraveller = true
            usersDbRef!!.child(ConstantValues.user!!.email.replace(".", "")).setValue(ConstantValues.user!!)
        }
    }

    override fun onGetAdvertInfoClicked(ad: Advert) {
        val bottomSheet = BottomSheetInfoAds.newInstance(ad)
        bottomSheet.show(supportFragmentManager, "TAG")
    }
}