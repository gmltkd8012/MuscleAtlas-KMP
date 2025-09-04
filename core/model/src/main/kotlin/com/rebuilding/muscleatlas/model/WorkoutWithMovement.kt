package com.rebuilding.muscleatlas.model

data class WorkoutWithMovement(
    val workoutData: WorkoutData,
    val movementList: List<MovementData>
)
