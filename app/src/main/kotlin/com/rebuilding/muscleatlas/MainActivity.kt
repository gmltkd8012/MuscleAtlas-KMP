package com.rebuilding.muscleatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.rebuilding.muscleatlas.main.screen.MainScreen
import com.rebuilding.muscleatlas.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun ProvideComposableScreen() {
        super.ProvideComposableScreen()
        MainScreen()
    }
}