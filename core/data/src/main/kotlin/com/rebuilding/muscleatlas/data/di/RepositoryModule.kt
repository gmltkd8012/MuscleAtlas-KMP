package com.rebuilding.muscleatlas.data.di

import com.rebuilding.muscleatlas.data.client.ClientRepository
import com.rebuilding.muscleatlas.data.client.ClientRepositoryImpl
import com.rebuilding.muscleatlas.room.dao.ClientDao
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
}