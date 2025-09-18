package com.rebuilding.muscleatlas.room.model

import android.net.Uri
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
    val imgUrl: Uri?,
    val name: String,
    val memo: String,
    val currentMills: Long,
)

fun ClientEntity.asExternalModel() = Client(
    id = id,
    imgUrl = imgUrl,
    name = name,
    memo = memo,
    currentMills = currentMills,
)

fun Client.asEntity() = ClientEntity(
    id = id,
    imgUrl = imgUrl,
    name = name,
    memo = memo,
    currentMills = currentMills,
)