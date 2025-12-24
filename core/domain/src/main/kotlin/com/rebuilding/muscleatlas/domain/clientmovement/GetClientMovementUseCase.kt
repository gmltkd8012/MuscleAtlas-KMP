package com.rebuilding.muscleatlas.domain.clientmovement

import com.rebuilding.muscleatlas.data.clientmovement.ClientMovementRepository
import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.data.workout.WorkoutRepository
import com.rebuilding.muscleatlas.model.ClientMovementData
import com.rebuilding.muscleatlas.model.Contraction
import com.rebuilding.muscleatlas.model.MovementByClientData
import com.rebuilding.muscleatlas.model.WorkoutWithClientMovement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetClientMovementUseCase @Inject constructor(
    private val workRepository: WorkoutRepository,
    private val movementRepository: MovementRepository,
    private val clientMovementRepository: ClientMovementRepository,
) {

    suspend operator fun invoke(clientId: String, workoutId: String): Flow<WorkoutWithClientMovement> = combine(
        workRepository.getWorkoutById(workoutId),
        movementRepository.getMovementByWorkoutId(workoutId),
        clientMovementRepository.getClientMovementsByWorkoutId(clientId, workoutId),
    ) { workout, movements, clientMovements ->

        val clientMovementMap = clientMovements.associateBy { it.movementId }

        val concentricMovementList = movements.filter { it.contraction == Contraction.Concentric.value }
            .map { movement ->
                MovementByClientData(
                    movementData = movement,
                    clientMovementData = clientMovementMap[movement.id] ?: ClientMovementData()
                )
            }

        val eccentricMovementList = movements.filter { it.contraction == Contraction.Eccentric.value }
            .map { movement ->
                MovementByClientData(
                    movementData = movement,
                    clientMovementData = clientMovementMap[movement.id] ?: ClientMovementData()
                )
            }

        WorkoutWithClientMovement(
            workoutData = workout,
            concentricMovementList = concentricMovementList,
            eccentricMovementList = eccentricMovementList,
        )
    }
}