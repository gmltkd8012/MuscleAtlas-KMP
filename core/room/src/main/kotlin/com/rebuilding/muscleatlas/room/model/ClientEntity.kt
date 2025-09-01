package com.rebuilding.muscleatlas.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rebuilding.muscleatlas.model.Client
import java.util.UUID

@Entity(
    tableName = "clients"
)
data class ClientEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val memo: String,
)

fun ClientEntity.asExternalModel() = Client(
    id = id,
    name = name,
    memo = memo,
)

fun Client.asEntity() = ClientEntity(
    id = id,
    name = name,
    memo = memo,
)