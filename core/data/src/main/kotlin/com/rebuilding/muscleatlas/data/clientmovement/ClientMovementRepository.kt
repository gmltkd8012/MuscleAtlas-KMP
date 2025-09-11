package com.rebuilding.muscleatlas.data.clientmovement

import com.rebuilding.muscleatlas.model.ClientMovementData
import kotlinx.coroutines.flow.Flow

interface ClientMovementRepository {

    suspend fun insertClientMovement(clientMovement: List<ClientMovementData>)

    suspend fun getClientMovementsByWorkoutId(clientId: String, workoutId: String): Flow<List<ClientMovementData>>

    suspend fun updateClientMovement(clientId: String, movementId: String, isCompleted: Boolean)
}