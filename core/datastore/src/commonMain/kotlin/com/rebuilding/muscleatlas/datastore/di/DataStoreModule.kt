package com.rebuilding.muscleatlas.datastore.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.rebuilding.muscleatlas.datastore.DataStoreRepository
import com.rebuilding.muscleatlas.datastore.DataStoreRepositoryImpl
import com.rebuilding.muscleatlas.datastore.datastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    @Singleton
    @Provides
    @Named("settings")
    fun provideDataStore(
        application: Application
    ): DataStore<Preferences> = application.datastore

    @Provides
    fun provideDataStoreRepository(
        @Named("settings") dataStore: DataStore<Preferences>
    ): DataStoreRepository = DataStoreRepositoryImpl(dataStore)
}