package com.rebuilding.muscleatlas.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.rebuilding.muscleatlas.setting.screen.SettingScreen
import com.rebuilding.muscleatlas.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun ProvideComposableScreen() {
        super.ProvideComposableScreen()
        val destination = intent.getStringExtra("destination") ?: ""

        SettingScreen(
            destination = destination,
            onClickBack = { finish() }
        )
    }
}