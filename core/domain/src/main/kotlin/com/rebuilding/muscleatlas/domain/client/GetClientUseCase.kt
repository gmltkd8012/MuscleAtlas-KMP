package com.rebuilding.muscleatlas.domain.client

import com.rebuilding.muscleatlas.data.client.ClientRepository
import com.rebuilding.muscleatlas.model.Client
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository
) {

    suspend operator fun invoke(): Flow<List<Client>> {
        return clientRepository.getAllClient()
    }
}