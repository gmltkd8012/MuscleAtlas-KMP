package com.rebuilding.muscleatlas.domain.movement

import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.model.Contraction
import com.rebuilding.muscleatlas.model.MovementData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovementsByContractionUseCase @Inject constructor(
    private val movementRepository: MovementRepository,
) {

    suspend operator fun invoke(
        workoutId: String,
        contraction: Contraction
    ): Flow<List<MovementData>> =
        movementRepository.getMovementsByContraction(contraction)
}