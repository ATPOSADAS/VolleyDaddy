package com.mobiledev.volleydaddy.db

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobiledev.volleydaddy.dao.RotationDao
import com.mobiledev.volleydaddy.dao.TeamDao
import com.mobiledev.volleydaddy.model.RoomRotationData
import com.mobiledev.volleydaddy.model.RoomTeam

@Database(entities = [RoomTeam.Team::class, RoomTeam.TeamMember::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        var db_instance: AppDatabase? = null

        fun getInstance(context: Context): Pair<AppDatabase, Boolean>{
            if (db_instance == null){
                db_instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app-db"
                ).allowMainThreadQueries().build()
                return Pair(db_instance as AppDatabase, true)
            }
            return Pair(db_instance as AppDatabase, false)
        }
    }


    abstract fun teamDao(): TeamDao

}