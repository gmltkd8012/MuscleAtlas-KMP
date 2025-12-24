package com.rebuilding.muscleatlas.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rebuilding.muscleatlas.room.model.MovementEntity
import com.rebuilding.muscleatlas.room.model.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovementDao {
    @Query("SELECT * FROM movements ORDER BY currentMills ASC")
    fun getAllMovements(): Flow<List<MovementEntity>>

    @Query("SELECT * FROM movements WHERE type = :type ORDER BY currentMills ASC")
    fun getMovementsByType(type: Int): Flow<List<MovementEntity>>

    @Query("SELECT * FROM movements WHERE workoutId = :workoutId ORDER BY currentMills ASC")
    fun getAllMovementsByWorkoutId(workoutId: String): Flow<List<MovementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovement(movement: List<MovementEntity>)

    @Query("DELETE FROM movements WHERE id = :movementId")
    suspend fun deleteMovement(movementId: String)

    @Query("DELETE FROM movements WHERE workoutId = :workoutId")
    suspend fun deleteMovemenetsByWorkoutId(workoutId: String)

}