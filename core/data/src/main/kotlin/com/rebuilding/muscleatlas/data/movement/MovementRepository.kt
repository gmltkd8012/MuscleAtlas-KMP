package com.rebuilding.muscleatlas.data.movement

import com.rebuilding.muscleatlas.model.Contraction
import com.rebuilding.muscleatlas.model.MovementData
import kotlinx.coroutines.flow.Flow

interface MovementRepository {

    suspend fun getAllMovements(): Flow<List<MovementData>>

    suspend fun getMovementsByType(type: Int): Flow<List<MovementData>>

    suspend fun getMovementsByContraction(contraction: Contraction): Flow<List<MovementData>>

    suspend fun getMovementByWorkoutId(workoutId: String): Flow<List<MovementData>>

    suspend fun updateMovement(movement: List<MovementData>)

    suspend fun deleteMovement(id: String)
}