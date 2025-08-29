package com.rebuilding.muscleatlas.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.setting.screen.SettingScreen
import com.rebuilding.muscleatlas.setting.viewmodel.SettingViewModel
import com.rebuilding.muscleatlas.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity() {

    private val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val destination = intent.getStringExtra("destination") ?: ""
            val darkMode = intent.getStringExtra("isdarktheme") ?: ""
            val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle(false, this)

            SettingScreen(
                isDarkTheme = isDarkTheme == AppTheme.Dark.id,
                destination = destination,
                onClickBack = { finish() }
            )
        }
    }
}