package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.MovementData

data class WorkoutAddState(
    val joinMovementList: List<MovementData> = emptyList<MovementData>(),
    val stabilizationMechanismList: List<MovementData> = emptyList<MovementData>(),
    val neuromuscularRelationList: List<MovementData> = emptyList<MovementData>(),
)