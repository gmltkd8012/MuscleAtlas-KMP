package com.rebuilding.muscleatlas.model

data class DeleteMovementDialogData(
    val isShown: Boolean = false,
    val movement: MovementData? = null,
)