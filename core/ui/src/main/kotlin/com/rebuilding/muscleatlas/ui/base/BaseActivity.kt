package com.rebuilding.muscleatlas.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.rebuilding.muscleatlas.ui.theme.RootScreen

open class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RootScreen {
                ProvideComposableScreen()
            }
        }
    }

    @Composable
    open fun ProvideComposableScreen() {}
}