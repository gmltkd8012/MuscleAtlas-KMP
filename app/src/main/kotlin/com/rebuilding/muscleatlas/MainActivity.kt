package com.rebuilding.muscleatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rebuilding.muscleatlas.design_system.theme.MuscleAtlasTheme
import com.rebuilding.muscleatlas.main.screen.MainScreen
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.ui.base.BaseActivity
import com.rebuilding.muscleatlas.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(false)

            MuscleAtlasTheme(darkTheme = isDarkTheme == AppTheme.Dark.id) {
                MainScreen(
                    isDarkTheme = isDarkTheme == AppTheme.Dark.id
                )
            }
        }
    }

    @Composable
    override fun ProvideComposableScreen() {
        super.ProvideComposableScreen()

    }
}