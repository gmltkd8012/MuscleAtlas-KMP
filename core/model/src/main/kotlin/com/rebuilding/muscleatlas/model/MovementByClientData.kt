package com.rebuilding.muscleatlas.model

data class MovementByClientData(
    val movementData: MovementData = MovementData(),
    val clientMovementData: ClientMovementData = ClientMovementData(),
)
