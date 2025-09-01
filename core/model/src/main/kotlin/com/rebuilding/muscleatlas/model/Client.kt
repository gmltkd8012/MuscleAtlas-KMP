package com.rebuilding.muscleatlas.model

import java.util.UUID

data class Client(
    val id: String,
    val imgUrl: String?,
    val name: String,
    val memo: String,
    val currentMills: Long,
)