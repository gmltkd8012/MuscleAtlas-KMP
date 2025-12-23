package com.rebuilding.muscleatlas.domain.client

import com.rebuilding.muscleatlas.data.client.ClientRepository
import javax.inject.Inject

class DeleteClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository,
) {

    suspend operator fun invoke(id: String) {
        clientRepository.deleteClient(id)
    }
}