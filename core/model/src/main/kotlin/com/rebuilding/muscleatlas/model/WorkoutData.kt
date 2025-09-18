package com.rebuilding.muscleatlas.model

import android.net.Uri

data class WorkoutData(
    val id: String = "",
    val imgUrl: Uri? = null,
    val title: String = "",
    val description: String = "",
    val currentMills: Long = 0L,
)