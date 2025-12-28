package com.rebuilding.muscleatlas.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.setting.screen.SettingScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute : Route

fun NavGraphBuilder.settingScreen() {
    composable<SettingRoute> {
        SettingScreen()
    }
}
