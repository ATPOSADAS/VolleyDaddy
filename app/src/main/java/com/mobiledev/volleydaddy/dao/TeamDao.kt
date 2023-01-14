package com.mobiledev.volleydaddy.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mobiledev.volleydaddy.model.RoomTeam

@Dao
interface TeamDao {

    @Transaction
    @Query("SELECT * FROM TeamMember WHERE TeamMember.teamId = :teamId")
    fun getMembers(teamId: Int): List<RoomTeam.TeamMember>

    @Transaction
    @Query("SELECT * FROM Team")
    fun getTeams(): List<RoomTeam.Team>

    @Transaction
    @Query("SELECT * FROM Team")
    fun getTeamsWithMembers(): List<RoomTeam.TeamWithMembers>

    @Transaction
    @Query("SELECT * FROM Team WHERE teamId = :id")
    fun getTeamWithMembers(id: Int ): RoomTeam.TeamWithMembers

    @Transaction
    @Query("SELECT * FROM Team WHERE team_name LIKE :name")
    fun getTeamWithMembers(name: String ): RoomTeam.TeamWithMembers

    @Transaction
    @Query("SELECT * FROM Team WHERE team_name LIKE :name")
    fun getTeam(name: String ): RoomTeam.Team

    @Transaction
    @Query("SELECT * FROM Team WHERE teamId = :id")
    fun getTeam(id: Int ): RoomTeam.Team

    @Insert
    fun insertTeamsAndMembers(team: RoomTeam.Team, members: List<RoomTeam.TeamMember>)

    @Insert
    fun insertTeam(team: RoomTeam.Team): Long

    @Insert
    fun insertMembers(members: List<RoomTeam.TeamMember>)
}