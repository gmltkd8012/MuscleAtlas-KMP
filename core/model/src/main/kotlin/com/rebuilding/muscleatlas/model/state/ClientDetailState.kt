package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData

data class ClientDetailState(
    val client: Client = Client(),
    val workoutList: List<WorkoutData> = emptyList<WorkoutData>(),
    val selectedWorkout: WorkoutData = WorkoutData(),
    val concentric: ContractionTypeList = ContractionTypeList(),
    val eccentric: ContractionTypeList = ContractionTypeList(),
)
