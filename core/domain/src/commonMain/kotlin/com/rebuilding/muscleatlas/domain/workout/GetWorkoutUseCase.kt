package com.rebuilding.muscleatlas.domain.workout

import com.rebuilding.muscleatlas.data.workout.WorkoutRepository
import com.rebuilding.muscleatlas.model.WorkoutData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
) {

    suspend operator fun invoke(): Flow<List<WorkoutData>> {
        return workoutRepository.getAllWorkouts()
    }
}