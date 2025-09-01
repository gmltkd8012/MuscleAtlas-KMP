package com.rebuilding.muscleatlas.model

import java.util.UUID

data class Client(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val memo: String,
)