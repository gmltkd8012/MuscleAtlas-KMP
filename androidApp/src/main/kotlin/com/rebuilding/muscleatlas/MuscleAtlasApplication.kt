package com.rebuilding.muscleatlas

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MuscleAtlasApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MuscleAtlasApplication)
            // modules(appModule) // TODO: Add your Koin modules here
        }
    }
}
