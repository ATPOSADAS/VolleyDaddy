package com.mobiledev.volleydaddy

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Prefs (context: Context){

    private var DARK_MODE_BOOL_PREF = "darkmode"

    private var SELECTED_TEAM_NAME = "teamName"

    private var SELECTED_TEAM_ID = "teamId"

    private val preferences: SharedPreferences = context.getSharedPreferences("PREFS", MODE_PRIVATE)

    var bool: Boolean
        get() = preferences.getBoolean(DARK_MODE_BOOL_PREF, false)
        set(value) = preferences.edit().putBoolean(DARK_MODE_BOOL_PREF, value).apply()

    var teamId: Int
        get() = preferences.getInt(SELECTED_TEAM_ID, -1)
        set(value) = preferences.edit().putInt(SELECTED_TEAM_ID, value).apply()

    var teamName: String?
        get() = preferences.getString(SELECTED_TEAM_NAME, "")
        set(value) = preferences.edit().putString(SELECTED_TEAM_NAME, value).apply()

}
