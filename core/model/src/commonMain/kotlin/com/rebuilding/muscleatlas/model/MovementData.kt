package com.rebuilding.muscleatlas.model

import android.net.Uri

data class MovementData(
    val id: String = "",
    val workoutId: String = "",
    val contraction: Int = -1,
    val type: Int = -1,
    val imgUrl: Uri? = null,
    val title: String = "",
    val description: String = "",
    val currentMills: Long = 0L,
)