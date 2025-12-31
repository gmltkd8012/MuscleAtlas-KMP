package com.rebuilding.muscleatlas.member.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.member.screen.MemberDetailScreen
import com.rebuilding.muscleatlas.member.screen.MemberScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object MemberRoute : Route

fun NavGraphBuilder.memberScreen(
    onNavigateToDetail: () -> Unit,
) {
    composable<MemberRoute> {
        MemberScreen(
            onNavigateToDetail = onNavigateToDetail,
        )
    }
}

@Serializable
data object MemberDetailRoute : Route

fun NavGraphBuilder.memberDetailScreen(
    onNavigateBack: () -> Unit
) {
    composable<MemberDetailRoute> {
        MemberDetailScreen(
            onNavigateBack = onNavigateBack,
        )
    }
}