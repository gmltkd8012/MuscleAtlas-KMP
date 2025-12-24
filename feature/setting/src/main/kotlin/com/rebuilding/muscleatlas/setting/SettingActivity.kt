package com.rebuilding.muscleatlas.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.setting.screen.SettingScreen
import com.rebuilding.muscleatlas.setting.viewmodel.SettingViewModel
import com.rebuilding.muscleatlas.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val destination = intent.getStringExtra("destination") ?: ""
            val isDarkTheme = intent.getBooleanExtra("theme", isSystemInDarkTheme())

            SettingScreen(
                isDarkTheme = isDarkTheme,
                destination = destination,
                onClickBack = { finish() }
            )
        }
    }
}