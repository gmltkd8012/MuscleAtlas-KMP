package com.rebuilding.muscleatlas.domain.workout

import com.rebuilding.muscleatlas.data.workout.WorkoutRepository
import com.rebuilding.muscleatlas.model.WorkoutData
import javax.inject.Inject

class UpdateWorkoutUseCase @Inject constructor(
    val workoutRepository: WorkoutRepository,
) {

    suspend operator fun invoke(workout: WorkoutData) {
        workoutRepository.updateWorkout(workout)
    }
}