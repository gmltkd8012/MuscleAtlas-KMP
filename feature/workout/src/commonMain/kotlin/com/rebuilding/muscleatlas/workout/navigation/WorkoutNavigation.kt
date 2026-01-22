package com.rebuilding.muscleatlas.workout.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rebuilding.muscleatlas.workout.screen.WorkoutScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import com.rebuilding.muscleatlas.workout.screen.WorkoutDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data object WorkoutRoute : Route

fun NavGraphBuilder.workoutScreen(
    onNavigateToDetail: (exerciseId: String) -> Unit,
    onNavigateToGroup: (groupId: String) -> Unit,
) {
    composable<WorkoutRoute> {
        WorkoutScreen(
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToGroup = onNavigateToGroup,
        )
    }
}

@Serializable
data class WorkoutDetailRoute(
    val exerciseId: String,
    val fromWorkoutScreen: Boolean = true, // 회원 상세 화면에서 접근 시, 편집 하지 않도록
) : Route

fun NavGraphBuilder.workoutDetailScreen(
    onNavigateBack: () -> Unit,
) {
    composable<WorkoutDetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<WorkoutDetailRoute>()
        WorkoutDetailScreen(
            exerciseId = route.exerciseId,
            fromWorkoutScreen = route.fromWorkoutScreen,
            onNavigateBack = onNavigateBack,
        )
    }
}
