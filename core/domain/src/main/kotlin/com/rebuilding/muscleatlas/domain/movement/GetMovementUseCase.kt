package com.rebuilding.muscleatlas.domain.movement

import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.model.MovementData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovementUseCase @Inject constructor(
    private val movementRepository: MovementRepository,
) {

    suspend operator fun invoke(workoutId: String?): Flow<List<MovementData>> {
        return movementRepository.getAllMovements()
    }
}