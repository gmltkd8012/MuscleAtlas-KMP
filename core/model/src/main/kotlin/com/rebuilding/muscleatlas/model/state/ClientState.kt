package com.rebuilding.muscleatlas.model.state

import com.rebuilding.muscleatlas.model.Client

data class ClientState(
    val clients: List<Client> = emptyList(),
)