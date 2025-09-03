package com.rebuilding.muscleatlas.room.di

import com.rebuilding.muscleatlas.room.MuscleAtlasDatabase
import com.rebuilding.muscleatlas.room.dao.ClientDao
import com.rebuilding.muscleatlas.room.dao.MovementDao
import com.rebuilding.muscleatlas.room.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesClientDao(
        database: MuscleAtlasDatabase
    ): ClientDao = database.clientDao()

    @Provides
    fun providesWorkoutDao(
        database: MuscleAtlasDatabase
    ): WorkoutDao = database.workoutDao()

    @Provides
    fun providesMovementDao(
        database: MuscleAtlasDatabase
    ): MovementDao = database.movementDao()
}