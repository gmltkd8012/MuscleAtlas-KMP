package com.rebuilding.muscleatlas.model

data class WorkoutData(
    val id: String = "",
    val imgUrl: String? = null,
    val title: String = "",
    val description: String = "",
    val currentMills: Long = 0L,
)