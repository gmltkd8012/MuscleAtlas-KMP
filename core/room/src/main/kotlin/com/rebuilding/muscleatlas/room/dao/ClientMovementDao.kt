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

    @Query("SELECT * FROM client_movement WHERE clientId = :clientId")
    fun getClientMovements(clientId: String): Flow<List<ClientMovementEntity>>

    @Query("UPDATE client_movement SET isCompleted = :isCompleted WHERE clientId = :clientId AND movementId = :movementId")
    suspend fun updateClientMovement(clientId: String, movementId: String, isCompleted: Boolean)
}