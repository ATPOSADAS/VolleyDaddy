package com.mobiledev.volleydaddy

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobiledev.volleydaddy.db.AppDatabase
import com.mobiledev.volleydaddy.model.RoomTeam
import com.mobiledev.volleydaddy.model.Team


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = R.id.rotationListFragment
    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting bottom nav with nav controller
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        createAndPrepopulateDatabase()
        checkForPreferences()
    }


    private fun checkForPreferences() {
        val sharedPref = prefs
        val nightMode: Boolean = sharedPref.bool
        if (nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            //Toast.makeText(this.applicationContext,"Night Mode",Toast.LENGTH_SHORT).show()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            //Toast.makeText(this.applicationContext,"Follow Mode",Toast.LENGTH_SHORT).show()
        }
    }

    private fun createAndPrepopulateDatabase() {
        var dbPair = AppDatabase.getInstance(this)
        // create default team
        val db = dbPair.first
        db.clearAllTables()
        val teamDao = db.teamDao()

        val team = teamDao.getTeam("Default")
        if (team == null) {
            val defaultTeam = RoomTeam.Team("Default")
            val teamId = teamDao.insertTeam(defaultTeam)
            teamDao.insertMembers(createMembers(teamId.toInt()))
        }
    }

    private fun createMembers(teamId: Int): List<RoomTeam.TeamMember> {
        var members = ArrayList<RoomTeam.TeamMember>()
        members.add(RoomTeam.TeamMember(teamId, RoomTeam.PositionType.SETTER, Team.PositionType.SETTER.name, 1))
        members.add(RoomTeam.TeamMember(teamId, RoomTeam.PositionType.RIGHT_SIDE_HITTER, Team.PositionType.RIGHT_SIDE_HITTER.name, 2))
        members.add(RoomTeam.TeamMember(teamId, RoomTeam.PositionType.MIDDLE_BLOCKER, Team.PositionType.MIDDLE_BLOCKER.name, 3))
        members.add(RoomTeam.TeamMember(teamId, RoomTeam.PositionType.OPPOSITE_HITTER, Team.PositionType.OPPOSITE_HITTER.name, 4))
        members.add(RoomTeam.TeamMember(teamId, RoomTeam.PositionType.OUTSIDE_HITTER, Team.PositionType.OUTSIDE_HITTER.name, 5))
        members.add(RoomTeam.TeamMember(teamId, RoomTeam.PositionType.LIBERO, Team.PositionType.LIBERO.name, 6))

        return members
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
