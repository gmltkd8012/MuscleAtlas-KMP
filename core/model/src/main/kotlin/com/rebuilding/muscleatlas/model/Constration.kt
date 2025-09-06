package com.rebuilding.muscleatlas.model


sealed class Constraction (
    val value: Int,
    val title: String,
    val description: String,
) {
    data object Concentric: Constraction(
        value = 0,
        title = "단축성 수축",
        description = "",
    )

    data object Eccentric: Constraction(
        value = 1,
        title = "신축성 수축",
        description = "",
    )

    companion object {
        val allConstrationTabs by lazy { listOf(Concentric.title, Eccentric.title) }
    }
}