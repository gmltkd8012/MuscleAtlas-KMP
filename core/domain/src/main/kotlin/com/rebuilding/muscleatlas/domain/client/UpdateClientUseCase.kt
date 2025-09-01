package com.rebuilding.muscleatlas.domain.client

import com.rebuilding.muscleatlas.data.client.ClientRepository
import com.rebuilding.muscleatlas.model.Client
import javax.inject.Inject

class UpdateClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository
) {

    operator suspend fun invoke(client: Client) {
        clientRepository.updateClient(client)
    }
}