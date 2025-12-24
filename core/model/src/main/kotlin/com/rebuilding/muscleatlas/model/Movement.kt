package com.rebuilding.muscleatlas.model

sealed class Movement (
    val title: String,
    val description: String,
) {
    data object JoinMovement: Movement(
        title = "관절 움직임",
        description = ""
    )

    data object StabilizationMechanism: Movement(
        title = "안정화 기전",
        description = ""
    )

    data object NeuromuscularRelation: Movement(
        title = "신경근 관계",
        description = ""
    )

    companion object {
        val allMovementTabs by lazy { listOf(JoinMovement.title, StabilizationMechanism.title, NeuromuscularRelation.title) }
    }
}

