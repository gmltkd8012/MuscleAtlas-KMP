package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData

data class ClientDetailState(
    val client: Client = Client(),
    val workoutList: List<WorkoutData> = emptyList<WorkoutData>(),
    val selectedWorkout: WorkoutData = WorkoutData(),
    val joinMovementList: List<MovementData> = emptyList<MovementData>(),
    val stabilizationMechanismList: List<MovementData> = emptyList<MovementData>(),
    val neuromuscularRelationList: List<MovementData> = emptyList<MovementData>(),
)
