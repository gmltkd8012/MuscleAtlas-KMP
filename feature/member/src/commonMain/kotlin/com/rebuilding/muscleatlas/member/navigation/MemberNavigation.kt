package com.rebuilding.muscleatlas.member.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rebuilding.muscleatlas.member.screen.MemberDetailScreen
import com.rebuilding.muscleatlas.member.screen.MemberScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object MemberRoute : Route

fun NavGraphBuilder.memberScreen(
    onNavigateToDetail: (memberId: String) -> Unit,
) {
    composable<MemberRoute> {
        MemberScreen(
            onNavigateToDetail = onNavigateToDetail,
        )
    }
}

@Serializable
data class MemberDetailRoute(val memberId: String) : Route

fun NavGraphBuilder.memberDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToWorkoutDetail: (exerciseId: String) -> Unit,
) {
    composable<MemberDetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<MemberDetailRoute>()
        MemberDetailScreen(
            memberId = route.memberId,
            onNavigateBack = onNavigateBack,
            onNavigateToWorkoutDetail = onNavigateToWorkoutDetail,
        )
    }
}