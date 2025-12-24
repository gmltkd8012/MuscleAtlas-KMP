package com.rebuilding.muscleatlas.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rebuilding.muscleatlas.room.model.ClientMovementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientMovementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClientMovement(clientMovement: List<ClientMovementEntity>)

    @Query("""
        SELECT cm.* FROM client_movement cm
        INNER JOIN movements m ON m.id = cm.movementId
        WHERE cm.clientId = :clientId AND m.workoutId = :workoutId
    """)
    fun getClientMovementsByWorkoutId(clientId: String, workoutId: String): Flow<List<ClientMovementEntity>>

    @Query("UPDATE client_movement SET isCompleted = :isCompleted WHERE clientId = :clientId AND movementId = :movementId")
    suspend fun updateClientMovement(clientId: String, movementId: String, isCompleted: Boolean)
}