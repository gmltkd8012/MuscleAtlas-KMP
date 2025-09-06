package com.rebuilding.muscleatlas.model

data class MovementData(
    val id: String = "",
    val workoutId: String = "",
    val contraction: Int = -1,
    val type: Int = -1,
    val imgUrl: String? = null,
    val title: String = "",
    val description: String = "",
    val currentMills: Long = 0L,
)

enum class Contraction(val value: Int) {
    CONCENTRIC(0), // 단축성 수축
    ECCENTRIC(1) // 신장성 수축
}