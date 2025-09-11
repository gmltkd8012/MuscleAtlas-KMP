package com.rebuilding.muscleatlas.data.clientmovement

import com.rebuilding.muscleatlas.model.ClientMovementData
import com.rebuilding.muscleatlas.room.dao.ClientMovementDao
import com.rebuilding.muscleatlas.room.model.ClientMovementEntity
import com.rebuilding.muscleatlas.room.model.asEntity
import com.rebuilding.muscleatlas.room.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientMovementRepositoryImpl @Inject constructor(
    private val clientMovementDao: ClientMovementDao,
) : ClientMovementRepository {

    override suspend fun insertClientMovement(clientMovement: List<ClientMovementData>) {
        clientMovementDao.insertClientMovement(clientMovement.map { it.asEntity() })
    }

    override suspend fun getClientMovements(clientId: String): Flow<List<ClientMovementData>> =
        clientMovementDao.getClientMovements(clientId)
            .map { it.map(ClientMovementEntity::asExternalModel) }

    override suspend fun updateClientMovement(
        clientId: String,
        movementId: String,
        isCompleted: Boolean
    ) {
        clientMovementDao.updateClientMovement(clientId, movementId, isCompleted)
    }
}