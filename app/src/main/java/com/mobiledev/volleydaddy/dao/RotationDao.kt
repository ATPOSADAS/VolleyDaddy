package com.mobiledev.volleydaddy.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mobiledev.volleydaddy.model.RoomRotationData

interface RotationDao {

    @Transaction
    @Query("SELECT * FROM NumberedRotation WHERE bigRotationId IS :bigRotationId")
    fun getNumberedRotations(bigRotationId: Int): List<RoomRotationData.NumberedRotation>

    @Transaction
    @Query("SELECT * FROM BigRotation")
    fun getBigRotations(): List<RoomRotationData.BigRotation>

    @Transaction
    @Query("SELECT * FROM BigRotation WHERE id IS :id")
    fun getBigRotationWithNumberedRotations(id: Int ): RoomRotationData.BigRotationWithNumberedRotations

    @Transaction
    @Query("SELECT * FROM TypedRotation WHERE numberedRotationId IS :numberId")
    fun getTypedRotations(numberId: Int): List<RoomRotationData.TypedRotation>

    @Transaction
    @Query("SELECT * FROM NumberedRotation WHERE numberId IS :numberId")
    fun getNumberedRotationWithTypedRotations(numberId: Int ): RoomRotationData.NumberedRotationWithTypedRotations


    @Insert
    fun insertBigRotation(insertBigRotation: RoomRotationData.BigRotation)

    @Insert
    fun insertNumberAndTypes(number: RoomRotationData.NumberedRotation, types: List<RoomRotationData.TypedRotation>)
}