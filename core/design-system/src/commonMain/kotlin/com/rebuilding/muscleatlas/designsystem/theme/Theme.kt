package com.rebuilding.muscleatlas.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(
        primary = AppColors.primaryDark,
        onPrimary = AppColors.onPrimaryDark,
        secondary = AppColors.secondaryDark,
        onSecondary = AppColors.onSecondaryDark,
        primaryContainer = AppColors.primaryButtonDark,
        onPrimaryContainer = AppColors.primaryButtonTextDark,
        error = AppColors.warning
    )

private val LightColorScheme =
    lightColorScheme(
        primary = AppColors.primaryLight,
        onPrimary = AppColors.onPrimaryLight,
        secondary = AppColors.secondaryLight,
        onSecondary = AppColors.onSecondaryLight,
        primaryContainer = AppColors.primaryButtonLight,
        onPrimaryContainer = AppColors.primaryButtonTextLight,
        error = AppColors.warning
    )

@Composable
fun MuscleAtlasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MuscleAtlasTypography,
        content = content
    )
}
