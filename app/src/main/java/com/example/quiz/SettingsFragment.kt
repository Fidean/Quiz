package com.example.quiz

import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            NavHostFragment.findNavController(this@SettingsFragment).navigate(R.id.action_settingsFragment_to_mainFragment)
        }

    }

}