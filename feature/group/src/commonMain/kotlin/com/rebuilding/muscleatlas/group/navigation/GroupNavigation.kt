package com.rebuilding.muscleatlas.group.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.group.screen.GroupScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object GroupRoute : Route

fun NavGraphBuilder.groupScreen(
    onNavigateBack: () -> Unit,
) {
    composable<GroupRoute> {
        GroupScreen(
            onNavigateBack = onNavigateBack,
        )
    }
}

