package com.rebuilding.muscleatlas.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.setting.screen.OpenSourceScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object OpenSourceRoute : Route

fun NavGraphBuilder.openSourceScreen(
    onNavigateBack: () -> Unit,
) {
    composable<OpenSourceRoute> {
        OpenSourceScreen(
            onNavigateBack = onNavigateBack,
        )
    }
}
