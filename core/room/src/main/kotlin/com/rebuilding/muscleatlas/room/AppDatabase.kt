package com.rebuilding.muscleatlas.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rebuilding.muscleatlas.room.dao.ClientDao
import com.rebuilding.muscleatlas.room.dao.ClientMovementDao
import com.rebuilding.muscleatlas.room.dao.MovementDao
import com.rebuilding.muscleatlas.room.dao.WorkoutDao
import com.rebuilding.muscleatlas.room.model.ClientEntity
import com.rebuilding.muscleatlas.room.model.ClientMovementEntity
import com.rebuilding.muscleatlas.room.model.MovementEntity
import com.rebuilding.muscleatlas.room.model.WorkoutEntity

/**************************************************************************************************
* title : App Database
*
* description : 앱 전체 DB
*
* @author LeeCoder
* @since 2025-08-19
**************************************************************************************************/

@Database(
    entities = [
        ClientEntity::class,
        WorkoutEntity::class,
        MovementEntity::class,
        ClientMovementEntity::class,
    ],
    version = 1
)
internal abstract class MuscleAtlasDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun movementDao(): MovementDao
    abstract fun clientMovementDao(): ClientMovementDao
}
