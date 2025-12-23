package com.rebuilding.muscleatlas.model

data class ClientWithWorkout(
    val client: Client,
    val workoutList: List<WorkoutData>
)
