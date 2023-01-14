package com.mobiledev.volleydaddy.model

import android.os.Parcel
import android.os.Parcelable
import androidx.constraintlayout.widget.ConstraintSet
import com.mobiledev.volleydaddy.R
import com.mobiledev.volleydaddy.data.DataSource
import com.mobiledev.volleydaddy.data.DataSource.rotations

data class RotationData(
    val rotationName: String) : Parcelable {
    private val id: Int = (0..1000).random()
    private var name: String = rotationName

    private var rotations: Array<MutableList<ConstraintSet>> = Array(6) {
        mutableListOf<ConstraintSet>()
        mutableListOf<ConstraintSet>()
        mutableListOf<ConstraintSet>()
        mutableListOf<ConstraintSet>()
        mutableListOf<ConstraintSet>()
        mutableListOf<ConstraintSet>()
    }

    constructor(parcel: Parcel) : this(parcel.readString().toString()) {
        name = parcel.readString().toString()
    }

    init {
        // fill the array with rotations
        for (i in 1..6) {

            val constraint_list = mutableListOf<ConstraintSet>()

            // make the four different states for each rotation
            val base_set = ConstraintSet()
            base_set.mIdString = "Rotation_{$i}_Base"
            val serve_set = ConstraintSet()
            serve_set.mIdString = "Rotation_{$i}_Serve"
            val receive_set = ConstraintSet()
            receive_set.mIdString = "Rotation_{$i}_Serve_Receive"
            val defense_set = ConstraintSet()
            defense_set.mIdString ="Rotation_{$i}_Defense"

            // add to the list
            constraint_list.add(base_set)
            constraint_list.add(serve_set)
            constraint_list.add(receive_set)
            constraint_list.add(defense_set)

            rotations[i-1] = constraint_list
        }

    }
    fun getIdd(): Int{
        return id;
    }

    enum class RotationType(val value: Int) {
        BASE(0),
        SERVE(1),
        RECEIVE(2),
        DEFENSE(3);

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }
    }

    fun setName(newName: String) {
        name = newName
    }

    fun getSize() = rotations.size

    fun getName() = name


    fun putRotation(number: Int, type: Int, set: ConstraintSet) {
        val list: MutableList<ConstraintSet> = rotations[number]
        list[type] = set
    }

    fun getRotationState(number: Int, type: Int): ConstraintSet {
        val list: MutableList<ConstraintSet> = rotations[number]
        return list[type]
    }

    fun getRotation(number: Int): MutableList<ConstraintSet> {
        return rotations[number]
    }

    fun getAllRotations() = rotations
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(rotationName)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RotationData> {
        override fun createFromParcel(parcel: Parcel): RotationData {
            return RotationData(parcel)
        }

        override fun newArray(size: Int): Array<RotationData?> {
            return arrayOfNulls(size)
        }
    }


}
