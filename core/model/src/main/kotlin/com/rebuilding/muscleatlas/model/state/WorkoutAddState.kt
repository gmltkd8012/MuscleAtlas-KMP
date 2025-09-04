package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData

data class WorkoutAddState(
    val workout: WorkoutData = WorkoutData(),
    val joinMovementList: List<MovementData> = emptyList<MovementData>(),
    val stabilizationMechanismList: List<MovementData> = emptyList<MovementData>(),
    val neuromuscularRelationList: List<MovementData> = emptyList<MovementData>(),
)