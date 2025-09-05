package com.rebuilding.muscleatlas.data.client

import android.R.attr.name
import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.room.dao.ClientDao
import com.rebuilding.muscleatlas.room.model.ClientEntity
import com.rebuilding.muscleatlas.room.model.asEntity
import com.rebuilding.muscleatlas.room.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepositoryImpl @Inject constructor(
    private val clientDao: ClientDao,
) : ClientRepository {

    override suspend fun getAllClient(): Flow<List<Client>> =
        clientDao.getAllClients()
            .map { it.map(ClientEntity::asExternalModel) }

    override suspend fun getClientById(id: String): Flow<Client> =
        clientDao.getClientById(id)
            .map(ClientEntity::asExternalModel)

    override suspend fun updateClient(client: Client) {
        clientDao.insertClient(client.asEntity())
    }

    override suspend fun deleteClient(id: String) {
        clientDao.deleteClient(id)
    }
}