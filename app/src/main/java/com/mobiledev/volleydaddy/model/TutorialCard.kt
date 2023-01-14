package com.mobiledev.volleydaddy.model

import androidx.annotation.DrawableRes

data class TutorialCard (
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val id: Int
)