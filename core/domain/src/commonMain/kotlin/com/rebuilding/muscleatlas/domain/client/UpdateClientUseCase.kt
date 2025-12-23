package com.rebuilding.muscleatlas.domain.client

import com.rebuilding.muscleatlas.data.client.ClientRepository
import com.rebuilding.muscleatlas.data.clientmovement.ClientMovementRepository
import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.model.Client
import com.rebuilding.muscleatlas.model.ClientMovementData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UpdateClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository,
    private val movementRepository: MovementRepository,
    private val clientMovementRepository: ClientMovementRepository,
) {

    suspend operator fun invoke(client: Client) {
        clientRepository.updateClient(client)

        val movements = movementRepository.getAllMovements().first()
            .map { movement ->
                ClientMovementData(
                    clientId = client.id,
                    movementId = movement.id,
                )
            }

        clientMovementRepository.insertClientMovement(movements)
    }
}