package com.rebuilding.muscleatlas.domain.movement

import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.model.MovementByType
import com.rebuilding.muscleatlas.util.MovementUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredMovementsByWorkoutUseCase @Inject constructor(
    private val movementRepository: MovementRepository,
) {

    suspend operator fun invoke(workoutId: String): Flow<MovementByType> {
        return movementRepository.getMovementByWorkoutId(workoutId).map {
            MovementByType(
                joinMovementList = it.filter { it.type == MovementUtils.TYPE_JOIN_MOVEMENT },
                stabilizationMechanismList = it.filter { it.type == MovementUtils.TYPE_STABILIZATION_MECHANISM },
                neuromuscularRelationList = it.filter { it.type == MovementUtils.TYPE_MUSCULAR_RELATION }
            )
        }
    }
}