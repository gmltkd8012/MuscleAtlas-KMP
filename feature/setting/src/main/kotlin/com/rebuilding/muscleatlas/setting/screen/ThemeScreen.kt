package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.setting.unit.theme.ThemeItemChip
import com.rebuilding.muscleatlas.setting.viewmodel.ThemeViewModel

@Composable
fun ThemeScreen(
    viewModel: ThemeViewModel = hiltViewModel<ThemeViewModel>()
) {
    val state by viewModel.state.collectAsState()

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            ThemeItemChip(
                mode = AppTheme.Light,
                selectedMode = state.mode,
                onClick = { mode ->
                    viewModel.setTheme(mode)
                }
            )

            Spacer(Modifier.height(8.dp))

            ThemeItemChip(
                mode = AppTheme.Dark,
                selectedMode = state.mode,
                onClick = { mode ->
                    viewModel.setTheme(mode)
                }
            )
        }
    }
}