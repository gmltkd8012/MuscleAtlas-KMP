package com.rebuilding.muscleatlas.room.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rebuilding.muscleatlas.model.WorkoutData

@Entity(
    tableName = "workouts"
)
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val imgUrl: Uri?,
    val title: String,
    val description: String,
    val currentMills: Long,
)

fun WorkoutEntity.asExternalModel() = WorkoutData(
    id = id,
    imgUrl = imgUrl,
    title = title,
    description = description,
    currentMills = currentMills,
)

fun WorkoutData.asEntity() = WorkoutEntity(
    id = id,
    imgUrl = imgUrl,
    title = title,
    description = description,
    currentMills = currentMills,
)