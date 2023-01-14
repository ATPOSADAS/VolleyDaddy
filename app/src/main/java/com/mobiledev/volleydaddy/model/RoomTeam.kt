package com.mobiledev.volleydaddy.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

class RoomTeam {

    enum class PositionType(val value: Int) {
        SETTER(0),
        RIGHT_SIDE_HITTER(1),
        MIDDLE_BLOCKER(2),
        OPPOSITE_HITTER(3),
        OUTSIDE_HITTER(4),
        LIBERO(5);

        companion object {
            fun fromInt(value: Int) = PositionType.values().first { it.value == value }
        }
    }

    @Entity
    data class Team(
        val team_name: String
    ){
        @PrimaryKey(autoGenerate = true) var teamId: Int = 0
    }
    @Entity
    data class TeamMember(
      val teamId: Int,
      val positionType: PositionType,
      val name: String,
      val number: Int

    ){
        @PrimaryKey(autoGenerate = true) var memberId: Int = 0
    }

    data class TeamWithMembers(
        @Embedded val team: Team,
        @Relation(
            parentColumn = "teamId",
            entityColumn = "memberId"
        )
        val members: List<TeamMember>
    )
}