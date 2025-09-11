package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.MovementByClientData
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.util.MovementUtils

data class ClientDetailState(
    val client: Client = Client(),
    val workoutList: List<WorkoutData> = emptyList<WorkoutData>(),
    val selectedWorkout: WorkoutData = WorkoutData(),
    val concentric: ContractionTypeWithClientMovementList = ContractionTypeWithClientMovementList(),
    val eccentric: ContractionTypeWithClientMovementList = ContractionTypeWithClientMovementList(),
)

data class ContractionTypeWithClientMovementList(
    val joinMovementList: List<MovementByClientData> = emptyList<MovementByClientData>(),
    val stabilizationMechanismList: List<MovementByClientData> = emptyList<MovementByClientData>(),
    val neuromuscularRelationList: List<MovementByClientData> = emptyList<MovementByClientData>(),
)

fun ContractionTypeWithClientMovementList.initWithClientMovement(
    movementByClientData: List<MovementByClientData>
): ContractionTypeWithClientMovementList {
    return copy(
        joinMovementList = movementByClientData.filter {
            it.movementData.type == MovementUtils.TYPE_JOIN_MOVEMENT
        },
        stabilizationMechanismList = movementByClientData.filter {
            it.movementData.type == MovementUtils.TYPE_STABILIZATION_MECHANISM
        },
        neuromuscularRelationList = movementByClientData.filter {
            it.movementData.type == MovementUtils.TYPE_MUSCULAR_RELATION
        }
    )
}