package com.rebuilding.muscleatlas.domain.clientmovement

import com.rebuilding.muscleatlas.data.clientmovement.ClientMovementRepository
import javax.inject.Inject

class UpdateClientMovementUseCase @Inject constructor(
    private val clientMovementRepository: ClientMovementRepository,
) {

    suspend operator fun invoke(clientId: String, movementId: String, isCompleted: Boolean) {
        clientMovementRepository.updateClientMovement(clientId, movementId, isCompleted)
    }
}