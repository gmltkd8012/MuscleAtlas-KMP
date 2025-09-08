package com.rebuilding.muscleatlas.data.movement

import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.room.dao.MovementDao
import com.rebuilding.muscleatlas.room.model.MovementEntity
import com.rebuilding.muscleatlas.room.model.asEntity
import com.rebuilding.muscleatlas.room.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovementRepositoryImpl @Inject constructor(
    private val movementDao: MovementDao
) : MovementRepository {

    override suspend fun getAllMovements(): Flow<List<MovementData>> =
        movementDao.getAllMovements()
            .map { it.map(MovementEntity::asExternalModel) }

    override suspend fun getMovementsByType(type: Int): Flow<List<MovementData>> =
        movementDao.getMovementsByType(type)
            .map { it.map(MovementEntity::asExternalModel) }

    override suspend fun getMovementByWorkoutId(workoutId: String): Flow<List<MovementData>> =
        movementDao.getAllMovementsByWorkoutId(workoutId)
            .map { it.map(MovementEntity::asExternalModel) }

    override suspend fun updateMovement(movement: List<MovementData>) {
        movementDao.insertMovement(
            movement.map { it.asEntity() }
        )
    }

    override suspend fun deleteMovement(id: String) {
        movementDao.deleteMovement(id)
    }

    override suspend fun clearMovements() {
        movementDao.clearMovemenets()
    }
}