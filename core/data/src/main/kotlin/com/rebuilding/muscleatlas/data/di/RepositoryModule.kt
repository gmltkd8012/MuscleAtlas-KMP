package com.rebuilding.muscleatlas.data.di

import com.rebuilding.muscleatlas.data.client.ClientRepository
import com.rebuilding.muscleatlas.data.client.ClientRepositoryImpl
import com.rebuilding.muscleatlas.data.movement.MovementRepository
import com.rebuilding.muscleatlas.data.movement.MovementRepositoryImpl
import com.rebuilding.muscleatlas.data.workout.WorkoutRepository
import com.rebuilding.muscleatlas.data.workout.WorkoutRepositoryImpl
import com.rebuilding.muscleatlas.room.dao.ClientDao
import com.rebuilding.muscleatlas.room.dao.MovementDao
import com.rebuilding.muscleatlas.room.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideClientRepository(
        clientDao: ClientDao
    ): ClientRepository {
        return ClientRepositoryImpl(clientDao)
    }

    @Provides
    fun provideWorkoutRepository(
        workoutDao: WorkoutDao
    ): WorkoutRepository{
        return WorkoutRepositoryImpl(workoutDao)
    }

    @Provides
    fun provideMovementRepository(
        movementDao: MovementDao
    ): MovementRepository {
        return MovementRepositoryImpl(movementDao)
    }
}