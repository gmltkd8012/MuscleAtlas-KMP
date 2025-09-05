package com.rebuilding.muscleatlas.model

data class MovementData(
    val id: String = "",
    val workoutId: String = "",
    val type: Int = -1,
    val imgUrl: String? = null,
    val title: String = "",
    val description: String = "",
    val currentMills: Long = 0L,
)