package com.rebuilding.muscleatlas.data.workout

import com.rebuilding.muscleatlas.model.WorkoutData
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun getAllWorkouts(): Flow<List<WorkoutData>>

    suspend fun updateWorkout(workout: WorkoutData)

    suspend fun deleteWorkout(id: String)
}