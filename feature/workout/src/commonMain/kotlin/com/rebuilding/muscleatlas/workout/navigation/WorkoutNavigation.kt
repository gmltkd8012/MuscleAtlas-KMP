package com.rebuilding.muscleatlas.workout.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.workout.screen.WorkoutScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import com.rebuilding.muscleatlas.workout.screen.WorkoutDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data object WorkoutRoute : Route

fun NavGraphBuilder.workoutScreen(
    onNavigateToDetail: () -> Unit,
) {
    composable<WorkoutRoute> {
        WorkoutScreen(
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Serializable
data object WorkoutDetailRoute : Route

fun NavGraphBuilder.workoutDetailScreen(
    onNavigateBack: () -> Unit,
) {
    composable<WorkoutDetailRoute> {
        WorkoutDetailScreen(
            onNavigateBack = onNavigateBack
        )
    }
}
