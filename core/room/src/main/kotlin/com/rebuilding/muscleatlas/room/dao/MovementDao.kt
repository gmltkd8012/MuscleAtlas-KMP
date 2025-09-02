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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovement(movement: MovementEntity)

    @Query("DELETE FROM movements WHERE id = :movementId")
    suspend fun deleteMovement(movementId: String)

}