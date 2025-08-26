package com.rebuilding.muscleatlas.design_system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object AppColors {
    val color: AppColorScheme
        @Composable
        get() = LocalAppColors.current

    val primaryLight = Color.White
    val secondaryLight = Color(0xFFF3F4F6)

    val onPrimaryLight = Color(0xFF111827)
    val onSecondaryLight = Color(0xFF6B7280)

    val primaryButtonLight = Color(0xFF4F46E5)
    val primaryButtonTextLight = Color.White



    val primaryDark = Color(0xFF111827)
    val secondaryDark = Color(0xFF1A1F2C)

    val onPrimaryDark = Color.White
    val onSecondaryDark = Color(0xFF9CA3AF)

    val primaryButtonDark = Color(0xFF311D78)
    val primaryButtonTextDark = Color.White



    val warning = Color.Red
}

@Immutable
data class AppColorScheme(
    val primary: Color,
    val secondary: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val primaryButton: Color,
    val primaryButtonText: Color,
) {
    companion object {
        val LightMode = AppColorScheme(
            primary = AppColors.primaryLight,
            secondary = AppColors.secondaryLight,
            onPrimary = AppColors.onPrimaryLight,
            onSecondary = AppColors.onSecondaryLight,
            primaryButton = AppColors.primaryButtonLight,
            primaryButtonText = AppColors.primaryButtonTextLight,
        )

        val DarkMode = AppColorScheme(
            primary = AppColors.primaryDark,
            secondary = AppColors.secondaryDark,
            onPrimary = AppColors.onPrimaryDark,
            onSecondary = AppColors.onSecondaryDark,
            primaryButton = AppColors.primaryButtonDark,
            primaryButtonText = AppColors.primaryButtonTextDark,
        )
    }
}

internal val LocalAppColors = staticCompositionLocalOf { AppColorScheme.LightMode }