package com.rebuilding.muscleatlas.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.setting.screen.AccountScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object AccountRoute : Route

fun NavGraphBuilder.accountScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
) {
    composable<AccountRoute> {
        AccountScreen(
            onNavigateBack = onNavigateBack,
            onLogout = onLogout,
        )
    }
}

fun NavController.navigateToAccount() {
    navigate(AccountRoute)
}
