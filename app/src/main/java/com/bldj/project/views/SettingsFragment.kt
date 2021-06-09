package com.bldj.project.views

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import com.bldj.project.databinding.SettingsLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import data.ConstantValues
import data.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsFragment : Fragment() {
    private var advertsDbRef: DatabaseReference? = null
    lateinit var inflaterThis: View
    private lateinit var name: EditText
    private lateinit var lastname: EditText
    private lateinit var settingsLayoutBinding: SettingsLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val appSettingPrefs: SharedPreferences = this.requireActivity()
            .getSharedPreferences("AppSettingPrefs", 0)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)
        settingsLayoutBinding = SettingsLayoutBinding.inflate(inflater,container,false)
        //inflaterThis = inflater.inflate(R.layout.settings_layout, container, false)

        //val switchBtn: Button = inflaterThis.findViewById(R.id.switch_btn)

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            settingsLayoutBinding.switchBtn.text = "Disable Dark Mode"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            settingsLayoutBinding.switchBtn.text = "Enable Dark Mode"
        }

        settingsLayoutBinding.switchBtn.setOnClickListener(View.OnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("NightMode", false)
                sharedPrefsEdit.apply()

                settingsLayoutBinding.switchBtn.text = "Enable Dark Mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("NightMode", true)
                sharedPrefsEdit.apply()

                settingsLayoutBinding.switchBtn.text = "Disable Dark Mode"
            }
        })
        settingsLayoutBinding.backButton.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStackImmediate();
            }
        }
        name = settingsLayoutBinding.enterName
        lastname = settingsLayoutBinding.enterLastname
        val usersDbRef =
            ConstantValues.database?.reference?.child(ConstantValues.USER_DB_REFERENCE)
        settingsLayoutBinding.editName.setOnClickListener {
            if(name.text.toString().isNotBlank() && lastname.text.toString().isNotBlank()){
                val usersChildEventListener = object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        runBlocking {
                            launch {
                                val user: User = snapshot.getValue(User::class.java)!!
                                if (user.id == FirebaseAuth.getInstance().currentUser.uid) {
                                    user.name = name.text.toString() + " " + lastname.text.toString()
                                    usersDbRef!!.child(user.email.replace(".","")).setValue(user)
                                }
                            }
                        }
                    }

                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                    override fun onChildRemoved(snapshot: DataSnapshot) {}

                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                    override fun onCancelled(error: DatabaseError) {}
                }
                usersDbRef?.addChildEventListener(usersChildEventListener as ChildEventListener)
            }

        }

        //return inflaterThis
        return settingsLayoutBinding.root
    }
}