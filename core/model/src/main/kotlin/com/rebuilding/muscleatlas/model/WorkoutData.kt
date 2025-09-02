package com.rebuilding.muscleatlas.model

data class WorkoutData(
    val id: String,
    val imgUrl: String?,
    val title: String,
    val description: String,
    val currentMills: Long,
)