package com.rebuilding.muscleatlas.design_system.theme

import android.app.Application
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rebuilding.muscleatlas.design_system.AppColorScheme
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.LocalAppColors
import com.rebuilding.muscleatlas.model.AppTheme

@Composable
fun MuscleAtlasTheme(
    themeMode: String = AppTheme.Light.id,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    val darkTheme = when(themeMode) {
        AppTheme.Dark.id -> true
        else -> false
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = !darkTheme,
        )
    }

    val colorScheme = when {
        darkTheme -> AppColorScheme.DarkMode
        else -> AppColorScheme.LightMode
    }

    CompositionLocalProvider(
        LocalAppColors provides colorScheme
    ) {
        content()
    }
}
