package com.rebuilding.muscleatlas.model

data class MovementByType(
    val joinMovementList: List<MovementData> = emptyList<MovementData>(),
    val stabilizationMechanismList: List<MovementData> = emptyList<MovementData>(),
    val neuromuscularRelationList: List<MovementData> = emptyList<MovementData>(),
)