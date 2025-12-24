package com.rebuilding.muscleatlas.domain.movement

import com.rebuilding.muscleatlas.data.client.ClientRepository
import com.rebuilding.muscleatlas.data.clientmovement.ClientMovementRepository
import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.model.ClientMovementData
import com.rebuilding.muscleatlas.model.MovementData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateMovementUseCase @Inject constructor(
    private val clientRepository: ClientRepository,
    private val movementRepository: MovementRepository,
    private val clientMovementRepository: ClientMovementRepository,
) {

    suspend operator fun invoke(
        workoutId: String,
        movements: List<MovementData>
    ) {
        movementRepository.deleteMovementsByWorkoutId(workoutId)
        movementRepository.updateMovement(movements)

        val clientMovements = clientRepository.getAllClient().first()
            .flatMap { client ->
                movements.map { movement ->
                    ClientMovementData(
                        clientId = client.id,
                        movementId = movement.id,
                    )
                }
            }

        clientMovementRepository.insertClientMovement(clientMovements)
    }
}