package com.mobiledev.volleydaddy.model

import androidx.constraintlayout.widget.ConstraintSet
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

class RoomRotationData {

    enum class RotationType(val value: Int) {
        BASE(0),
        SERVE(1),
        RECEIVE(2),
        DEFENSE(3);

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }
    }

    @Entity
    data class BigRotation(
        val name: String
    ){
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    }

    @Entity
    data class NumberedRotation(
        val bigRotationId: Int
    ){
        @PrimaryKey(autoGenerate = true) var numberId: Int = 0
    }

    @Entity
    data class TypedRotation(
        val numberedRotationId: Int,
        val rotationType: RotationData.RotationType,
        val constraintSet: ConstraintSet
    ){
        @PrimaryKey(autoGenerate = true) var typeId: Int = 0

    }

    data class BigRotationWithNumberedRotations(
        @Embedded val big: BigRotation,
        @Relation(
            parentColumn = "id",
            entityColumn = "numberId"
        )
        val numberedRotations: List<NumberedRotation>
    )

    data class NumberedRotationWithTypedRotations(
        @Embedded val numbered: NumberedRotation,
        @Relation(
            parentColumn = "numberId",
            entityColumn = "typeId"
        )
        val typedRotations: List<TypedRotation>
    )


}