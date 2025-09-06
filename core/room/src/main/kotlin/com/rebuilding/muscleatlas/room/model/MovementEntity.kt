package com.rebuilding.muscleatlas.room.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.rebuilding.muscleatlas.model.MovementData

@Entity(
    tableName = "movements",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovementEntity(
    @PrimaryKey
    val id: String, // PK
    val workoutId: String, // FK
    val contraction: Int, // 수축 유형
    val type: Int, // 세부 유형
    val imgUrl: String?,
    val title: String,
    val description: String,
    val currentMills: Long,
)

fun MovementEntity.asExternalModel() = MovementData(
    id = id,
    workoutId = workoutId,
    contraction = contraction,
    type = type,
    imgUrl = imgUrl,
    title = title,
    description = description,
    currentMills = currentMills,
)

fun MovementData.asEntity() = MovementEntity(
    id = id,
    workoutId = workoutId,
    contraction = contraction,
    type = type,
    imgUrl = imgUrl,
    title = title,
    description = description,
    currentMills = currentMills,
)