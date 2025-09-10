package com.rebuilding.muscleatlas.room.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rebuilding.muscleatlas.model.ClientMovementData

@Entity(
    tableName = "client_movement",
    foreignKeys = [
        ForeignKey(
            entity = ClientEntity::class,
            parentColumns = ["id"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MovementEntity::class,
            parentColumns = ["id"],
            childColumns = ["movementId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["clientId", "movementId"], unique = true)
    ]
)
data class ClientMovementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // PK
    val clientId: String, // Client FK
    val movementId: String, // Movement FK
    val isCompleted: Boolean = false,
)


fun ClientMovementEntity.asExternalModel() = ClientMovementData(
    id = id,
    clientId = clientId,
    movementId = movementId,
    isCompleted = isCompleted,
)

fun ClientMovementData.asEntity() = ClientMovementEntity(
    id = id,
    clientId = clientId,
    movementId = movementId,
    isCompleted = isCompleted,
)
