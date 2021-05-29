package com.bldj.project

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.bldj.project.databinding.SettingsLayoutBinding
import data.IBackButton

class SettingsFragment : Fragment(), IBackButton {
    lateinit var inflaterThis: View
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
        //return inflaterThis
        return settingsLayoutBinding.root
    }

    override fun onBackPressed(): Boolean {
        TODO("Not yet implemented")
    }
}