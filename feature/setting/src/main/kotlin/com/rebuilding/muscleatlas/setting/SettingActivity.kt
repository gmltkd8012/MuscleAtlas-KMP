package com.rebuilding.muscleatlas.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rebuilding.muscleatlas.setting.screen.SettingScreen

class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val destination = intent.getStringExtra("destination") ?: ""

            SettingScreen(
                destination = destination,
                onClickBack = { finish() }
            )
        }
    }
}