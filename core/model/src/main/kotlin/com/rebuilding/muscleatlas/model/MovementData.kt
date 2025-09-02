package com.rebuilding.muscleatlas.model

data class MovementData(
    val id: String,
    val workoutId: String,
    val type: Int,
    val imgUrl: String?,
    val title: String,
    val description: String,
    val currentMills: Long,
)