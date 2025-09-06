package com.rebuilding.muscleatlas.model

data class WorkoutWithMovement(
    val workoutData: WorkoutData,
    val concentricMovementList: List<MovementData>,
    val eccentricMovementList: List<MovementData>,
)
