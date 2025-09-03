package com.rebuilding.muscleatlas.domain.workout

import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.data.workout.WorkoutRepository
import javax.inject.Inject

class GetWorkoutWithMovementUseCase @Inject constructor(
    private val workRepository: WorkoutRepository,
    private val movementRepository: MovementRepository,
) {


    suspend operator fun invoke() {

    }


}