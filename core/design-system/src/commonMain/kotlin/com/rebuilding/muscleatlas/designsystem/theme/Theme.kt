package com.rebuilding.muscleatlas.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(
        // Primary
        primary = AppColors.primaryDark,
        onPrimary = AppColors.onPrimaryDark,
        primaryContainer = AppColors.primaryContainerDark,
        onPrimaryContainer = AppColors.onPrimaryContainerDark,

        // Secondary
        secondary = AppColors.secondaryDark,
        onSecondary = AppColors.onSecondaryDark,
        secondaryContainer = AppColors.secondaryContainerDark,
        onSecondaryContainer = AppColors.onSecondaryContainerDark,

        // Tertiary
        tertiary = AppColors.tertiaryDark,
        onTertiary = AppColors.onTertiaryDark,
        tertiaryContainer = AppColors.tertiaryContainerDark,
        onTertiaryContainer = AppColors.onTertiaryContainerDark,

        // Background
        background = AppColors.backgroundDark,
        onBackground = AppColors.onBackgroundDark,

        // Surface
        surface = AppColors.surfaceDark,
        onSurface = AppColors.onSurfaceDark,
        surfaceVariant = AppColors.surfaceVariantDark,
        onSurfaceVariant = AppColors.onSurfaceVariantDark,
        surfaceContainerHighest = AppColors.surfaceContainerDark,

        // Outline
        outline = AppColors.outlineDark,
        outlineVariant = AppColors.outlineVariantDark,

        // Error
        error = AppColors.error,
        onError = AppColors.fixedWhite,
    )

private val LightColorScheme =
    lightColorScheme(
        // Primary
        primary = AppColors.primaryLight,
        onPrimary = AppColors.onPrimaryLight,
        primaryContainer = AppColors.primaryContainerLight,
        onPrimaryContainer = AppColors.onPrimaryContainerLight,

        // Secondary
        secondary = AppColors.secondaryLight,
        onSecondary = AppColors.onSecondaryLight,
        secondaryContainer = AppColors.secondaryContainerLight,
        onSecondaryContainer = AppColors.onSecondaryContainerLight,

        // Tertiary
        tertiary = AppColors.tertiaryLight,
        onTertiary = AppColors.onTertiaryLight,
        tertiaryContainer = AppColors.tertiaryContainerLight,
        onTertiaryContainer = AppColors.onTertiaryContainerLight,

        // Background
        background = AppColors.backgroundLight,
        onBackground = AppColors.onBackgroundLight,

        // Surface
        surface = AppColors.surfaceLight,
        onSurface = AppColors.onSurfaceLight,
        surfaceVariant = AppColors.surfaceVariantLight,
        onSurfaceVariant = AppColors.onSurfaceVariantLight,
        surfaceContainerHighest = AppColors.surfaceContainerLight,

        // Outline
        outline = AppColors.outlineLight,
        outlineVariant = AppColors.outlineVariantLight,

        // Error
        error = AppColors.error,
        onError = AppColors.fixedWhite,
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
