package com.rebuilding.muscleatlas.data.workout

import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.room.dao.WorkoutDao
import com.rebuilding.muscleatlas.room.model.WorkoutEntity
import com.rebuilding.muscleatlas.room.model.asEntity
import com.rebuilding.muscleatlas.room.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao,
) : WorkoutRepository {

    override suspend fun getAllWorkouts(): Flow<List<WorkoutData>> =
        workoutDao.getAllWorkouts()
            .map { it.map(WorkoutEntity::asExternalModel) }

    override suspend fun getWorkoutById(workoutId: String): Flow<WorkoutData> =
        workoutDao.getWorkoutById(workoutId)
            .map { it.asExternalModel() }

    override suspend fun updateWorkout(workout: WorkoutData) {
        workoutDao.insertWorkout(workout.asEntity())
    }

    override suspend fun deleteWorkout(id: String) {
        workoutDao.deleteWorkout(id)
    }
}