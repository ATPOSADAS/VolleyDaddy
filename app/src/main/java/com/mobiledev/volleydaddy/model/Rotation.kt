package com.mobiledev.volleydaddy.model

import java.lang.Exception

class Rotation (players: Array<Team.PositionType>, coordinates: List<Pair<Int, Int>>) {

    private val positions: MutableMap<Team.PositionType,  Pair<Int, Int>> = mutableMapOf()

    init {
        if (players.size != coordinates.size) {
            throw Exception("Rotation: Not all players have coordinates!!")
        }

        for (i in players.indices) {
            positions[players[i]] = coordinates[i]
        }
    }

    fun setPosition(position: Team.PositionType, numbers: Pair<Int, Int>){
        positions[position] = numbers
    }

    fun getPosition(position: Team.PositionType): Pair<Int, Int>? {
        return positions[position]
    }

    fun getAllPositions() = positions
}