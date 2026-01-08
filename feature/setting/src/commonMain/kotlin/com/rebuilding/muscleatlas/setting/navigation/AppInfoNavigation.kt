package com.rebuilding.muscleatlas.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.setting.screen.AppInfoScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object AppInfoRoute: Route

fun NavGraphBuilder.appInfoScreen(
    onNavigateBack: () -> Unit,
    onNavigateToOpenSource: () -> Unit,
) {
    composable<AppInfoRoute> {
        AppInfoScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToOpenSource = onNavigateToOpenSource,
        )
    }
}