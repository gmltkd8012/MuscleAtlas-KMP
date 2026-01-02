package com.rebuilding.muscleatlas.designsystem.theme

import androidx.compose.ui.graphics.Color

object AppColors {

    // ============================================
    // Light Theme Colors
    // ============================================

    // Background Colors
    val backgroundLight = Color(0xFFF8FAFC)         // Main app background
    val surfaceLight = Color.White                   // Cards, sheets, dialogs
    val surfaceVariantLight = Color(0xFFF1F5F9)     // Elevated surfaces
    val surfaceContainerLight = Color(0xFFE2E8F0)   // Input fields, containers

    // Primary Accent - Cyan (darker for light theme)
    val primaryLight = Color(0xFF2196F3)            // Primary actions, links
    val primaryContainerLight = Color(0xFFCFFAFE)   // Primary button backgrounds
    val onPrimaryLight = Color.White                 // Text on primary color
    val onPrimaryContainerLight = Color(0xFF0E7490) // Text on primary container

    // Secondary Accent - Blue
    val secondaryLight = Color(0xFF1976D2)
    val secondaryContainerLight = Color(0xFFE3F2FD)
    val onSecondaryLight = Color.White
    val onSecondaryContainerLight = Color(0xFF1565C0)

    // Tertiary Accent - Orange
    val tertiaryLight = Color(0xFFEA580C)
    val tertiaryContainerLight = Color(0xFFFFF7ED)
    val onTertiaryLight = Color.White
    val onTertiaryContainerLight = Color(0xFFC2410C)

    // Text Colors
    val onBackgroundLight = Color(0xFF0F172A)       // Primary text on background
    val onSurfaceLight = Color(0xFF334155)          // Body text on surfaces
    val onSurfaceVariantLight = Color(0xFF64748B)   // Muted/secondary text

    // Outline & Dividers
    val outlineLight = Color(0xFFCBD5E1)
    val outlineVariantLight = Color(0xFFE2E8F0)

    // ============================================
    // Dark Theme Colors (Based on Exercise Detail)
    // ============================================

    // Background Colors
    val backgroundDark = Color(0xFF0D1117)          // Main app background
    val surfaceDark = Color(0xFF161B22)             // Cards, sheets, dialogs
    val surfaceVariantDark = Color(0xFF1C2128)      // Elevated surfaces
    val surfaceContainerDark = Color(0xFF21262D)    // Input fields, containers

    // Primary Accent - Cyan
    val primaryDark = Color(0xFF22B8CF)             // Primary actions, links
    val primaryContainerDark = Color(0xFF0E4D5C)   // Primary button backgrounds
    val onPrimaryDark = Color(0xFF0D1117)           // Text on primary color
    val onPrimaryContainerDark = Color(0xFF22B8CF) // Text on primary container

    // Secondary Accent - Blue
    val secondaryDark = Color(0xFF2196F3)
    val secondaryContainerDark = Color(0xFF1E3A5F)
    val onSecondaryDark = Color(0xFF0D1117)
    val onSecondaryContainerDark = Color(0xFF4FC3F7)

    // Tertiary Accent - Orange
    val tertiaryDark = Color(0xFFE87B35)
    val tertiaryContainerDark = Color(0xFF5C3116)
    val onTertiaryDark = Color(0xFF0D1117)
    val onTertiaryContainerDark = Color(0xFFE87B35)

    // Text Colors
    val onBackgroundDark = Color.White              // Primary text on background
    val onSurfaceDark = Color(0xFFB0B8C1)          // Body text on surfaces
    val onSurfaceVariantDark = Color(0xFF8B949E)   // Muted/secondary text

    // Outline & Dividers
    val outlineDark = Color(0xFF30363D)
    val outlineVariantDark = Color(0xFF21262D)

    // ============================================
    // Semantic Colors
    // ============================================
    val success = Color(0xFF2EA043)
    val warning = Color(0xFFE87B35)
    val error = Color(0xFFF85149)
    val info = Color(0xFF2196F3)

    // ============================================
    // Legacy / Utility Colors
    // ============================================
    val clear = Color.Green
    val fixedWhite = Color.White

    // Shorthand aliases for convenience
    val accentCyan = primaryDark
    val accentOrange = tertiaryDark
    val accentBlue = secondaryDark
    val cardBackground = surfaceDark
    val sectionTitle = onSurfaceVariantDark
    val descriptionText = onSurfaceDark

    object Shadow {
        val Dialog = Color(0x1F000000)
    }
}