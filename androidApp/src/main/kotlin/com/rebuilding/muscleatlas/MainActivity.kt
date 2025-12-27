package com.rebuilding.muscleatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // muscleAtlasAppDeclaration은 KoinAppDeclaration을 반환하므로
        // startKoin()의 인자로 전달해야 함
        startKoin(
            muscleAtlasAppDeclaration {
                androidContext(this@MainActivity)
            }
        )

        setContent {
            App()
        }
    }
}
