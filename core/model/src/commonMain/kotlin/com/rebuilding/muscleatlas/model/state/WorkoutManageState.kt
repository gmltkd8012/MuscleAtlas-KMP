package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.WorkoutData

data class WorkoutManageState(
    val workouts: List<WorkoutData> = emptyList<WorkoutData>()
)