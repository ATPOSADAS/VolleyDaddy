package com.mobiledev.volleydaddy.data

import android.annotation.SuppressLint
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.content.res.TypedArrayUtils.getText
import com.mobiledev.volleydaddy.R
import com.mobiledev.volleydaddy.model.*

object DataSource {
    val tutorial_rotation_name = "Tutorial Resources"
    val five_one_rotation_name = "5-1 Rotation!"
    val six_two_rotation_name = "6-2 Rotation!"
    val base_rotation_name = "base"
    val rotations: MutableMap<String, RotationData> = mutableMapOf(
        five_one_rotation_name to RotationData(five_one_rotation_name),
        six_two_rotation_name to RotationData(six_two_rotation_name),
        base_rotation_name to RotationData(base_rotation_name)
    )

    val rotationCards: MutableList<RotationCard> = mutableListOf(
        RotationCard(
            R.drawable.rotations_explained,
            tutorial_rotation_name,
//            rotations[tutorial_rotation_name]!!.id
        0
        ),
        RotationCard(
            R.drawable.rotation_51,
            five_one_rotation_name,
//            rotations[five_one_rotation_name]!!.id
        1
        ),
        RotationCard(
            R.drawable.rotation_62,
            six_two_rotation_name,
//            rotations[six_two_rotation_name]!!.id
        2
        )
    )
    val tutorialCards: List<TutorialCard> = mutableListOf(
        TutorialCard(
            R.drawable.tutorial_points, // drag in new images in the resource file
            "How To Score Points", // filler text
            0 // don't worry about this, just put whatever
        ),
        TutorialCard(
            R.drawable.serve,
            "Serving and the Rotation System",
            1,
        ),
        TutorialCard(
            R.drawable.rotations_explained,
            "Positions Overview",
            2
        ),
        TutorialCard(
            R.drawable.volleyball,
            "More Information",
            3
        )
    )

}