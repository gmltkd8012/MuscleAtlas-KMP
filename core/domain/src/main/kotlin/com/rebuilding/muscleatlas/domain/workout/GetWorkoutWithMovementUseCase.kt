package com.rebuilding.muscleatlas.domain.workout

import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.data.workout.WorkoutRepository
import com.rebuilding.muscleatlas.model.Contraction
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutWithMovement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWorkoutWithMovementUseCase @Inject constructor(
    private val workRepository: WorkoutRepository,
    private val movementRepository: MovementRepository,
) {

    suspend operator fun invoke(workoutId: String): Flow<WorkoutWithMovement> = combine(
            workRepository.getWorkoutById(workoutId),
            movementRepository.getMovementByWorkoutId(workoutId)
        ) { workout, movements ->
            WorkoutWithMovement(
                workoutData = workout,
                concentricMovementList = movements.filter { it.contraction == Contraction.CONCENTRIC.value },
                eccentricMovementList = movements.filter { it.contraction == Contraction.ECCENTRIC.value },
            )
        }
}