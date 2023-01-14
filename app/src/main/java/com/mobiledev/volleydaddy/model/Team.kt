package com.mobiledev.volleydaddy.model

data class Team(val teamName: String){
    val name = teamName
    private val members: MutableMap<PositionType,  Pair<String, Int>> = mutableMapOf();
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

    fun addMember(position: PositionType, info: Pair<String, Int>){
        members[position] = info;
    }

    fun getMember(position: PositionType): Pair<String, Int>? {
        return members[position];
    }


}
