package com.rebuilding.muscleatlas.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rebuilding.muscleatlas.room.model.ClientEntity
import com.rebuilding.muscleatlas.room.model.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY currentMills ASC")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Query("DELETE FROM workouts WHERE id = :workoutId")
    suspend fun deleteWorkout(workoutId: String)


//    @Query("""
//        SELECT  w.id AS workout_id,
//                w.title AS workout_title,
//                m.id AS movement_id,
//                m.title AS movement_title,
//                m.type
//        FROM workouts w
//        JOIN movements m ON w.id = m.workoutId
//        WHERE w.id = :workoutId
//    """)
//    suspend fun getWorkoutWithMovement(): Flow<List<WorkoutEntity>>
}