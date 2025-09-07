package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.util.MovementUtils

data class WorkoutAddState(
    val workout: WorkoutData = WorkoutData(),
    val concentric: ContractionTypeList = ContractionTypeList(),
    val eccentric: ContractionTypeList = ContractionTypeList(),
)

data class ContractionTypeList(
    val joinMovementList: List<MovementData> = emptyList<MovementData>(),
    val stabilizationMechanismList: List<MovementData> = emptyList<MovementData>(),
    val neuromuscularRelationList: List<MovementData> = emptyList<MovementData>(),
)

fun ContractionTypeList.initWith(movements: List<MovementData>): ContractionTypeList {
   return copy(
       joinMovementList = movements
           .filter { it.type == MovementUtils.TYPE_JOIN_MOVEMENT },
       stabilizationMechanismList = movements
           .filter { it.type == MovementUtils.TYPE_STABILIZATION_MECHANISM },
       neuromuscularRelationList = movements
           .filter { it.type == MovementUtils.TYPE_MUSCULAR_RELATION }
   )
}

fun ContractionTypeList.updateWith(movement: MovementData): ContractionTypeList {
    return when(movement.type) {
        MovementUtils.TYPE_JOIN_MOVEMENT ->
            copy(joinMovementList = joinMovementList + movement)
        MovementUtils.TYPE_STABILIZATION_MECHANISM ->
            copy(stabilizationMechanismList = stabilizationMechanismList + movement)
        MovementUtils.TYPE_MUSCULAR_RELATION ->
            copy(neuromuscularRelationList = neuromuscularRelationList + movement)
        else -> this
    }
}

fun ContractionTypeList.saveWith(): List<MovementData> {
    return joinMovementList + stabilizationMechanismList + neuromuscularRelationList
}