package com.rebuilding.muscleatlas.domain.movement

import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.model.MovementData
import javax.inject.Inject

class UpdateMovementUseCase @Inject constructor(
    private val movementRepository: MovementRepository,
) {

    suspend operator fun invoke(movement: List<MovementData>) {
        movementRepository.updateMovement(movement)
    }
}