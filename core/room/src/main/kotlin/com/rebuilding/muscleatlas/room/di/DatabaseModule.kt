package com.rebuilding.muscleatlas.room.di

import android.content.Context
import android.database.DatabaseUtils
import androidx.room.Room
import com.rebuilding.muscleatlas.room.MuscleAtlasDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideMuscleAtlasDatabase(
        @ApplicationContext context: Context
    ): MuscleAtlasDatabase = Room.databaseBuilder(
        context,
        MuscleAtlasDatabase::class.java,
        "muscle_atlas_db"
    ).build()
}