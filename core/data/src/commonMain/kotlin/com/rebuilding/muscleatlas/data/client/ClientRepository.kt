package com.rebuilding.muscleatlas.data.client

import com.rebuilding.muscleatlas.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun getAllClient(): Flow<List<Client>>

    suspend fun getClientById(id: String): Flow<Client>

    suspend fun updateClient(client: Client)

    suspend fun deleteClient(id: String)
}