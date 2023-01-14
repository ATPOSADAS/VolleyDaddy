package com.mobiledev.volleydaddy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.*
import com.mobiledev.volleydaddy.db.AppDatabase
import com.mobiledev.volleydaddy.model.RoomTeam

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //fillOutTeams()
        val preference = findPreference<ListPreference>("team_select")!!
        preference.onPreferenceClickListener = object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick(preference: Preference?): Boolean {
                fillOutTeams(preference as ListPreference)
                return true
            }
        }
        setTeamIdPreference()
        setDarkModePreferences()
    }

    private fun setTeamIdPreference() {
        val darkModePreference = findPreference<ListPreference>("team_select")!!
        darkModePreference.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                prefs.teamId = (newValue as String).toInt()
                return true
            }
        }
    }

    private fun fillOutTeams(preference:ListPreference ) {
        var db = activity?.let { AppDatabase.getInstance(it.applicationContext) }!!.first
        var teamDao = db.teamDao()
        var teams = teamDao.getTeams();
        // Toast.makeText(context, teams.size.toString(), Toast.LENGTH_SHORT).show()
        var teamNames = getTeamNames(teams)
        var teamIds = getTeamIds(teams)

        //val preference = findPreference<ListPreference>("team_select")!!
        // var listPreference: ListPreference = preference as ListPreference
        preference.entries = teamNames
        preference.entryValues = teamIds
    }

    private fun getTeamIds(teams: List<RoomTeam.Team>): Array<CharSequence> {
        return teams.map { team -> team.teamId.toString() }.toTypedArray()
    }

    private fun setDarkModePreferences() {
        val darkModePreference = findPreference<SwitchPreference>("mode")!!
        darkModePreference.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                val nightMode: Boolean = newValue as Boolean
                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                    //Toast.makeText(context?.applicationContext,"Night Mode",Toast.LENGTH_SHORT).show()
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                    // Toast.makeText(context?.applicationContext,"Follow Mode",Toast.LENGTH_SHORT).show()
                }
                prefs.bool = nightMode
                return true
            }
        }
    }

    private fun getTeamNames(teams: List<RoomTeam.Team>): Array<CharSequence> {

        return teams.map { team -> team.team_name }.toTypedArray()
    }


}