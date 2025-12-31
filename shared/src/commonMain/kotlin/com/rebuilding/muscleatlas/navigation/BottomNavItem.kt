package com.rebuilding.muscleatlas.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rebuilding.muscleatlas.member.navigation.MemberRoute
import com.rebuilding.muscleatlas.setting.navigation.SettingRoute
import com.rebuilding.muscleatlas.ui.navigation.Route
import com.rebuilding.muscleatlas.workout.navigation.WorkoutRoute
import kotlinx.serialization.Serializable

// Main Screen Route (Bottom Navigation Container)
@Serializable
data object MainRoute : Route

enum class BottomNavItem(
    val route: Route,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    WORKOUT(
        route = WorkoutRoute,
        label = "운동",
        selectedIcon = Icons.Filled.FitnessCenter,
        unselectedIcon = Icons.Outlined.FitnessCenter,
    ),
    MEMBER(
        route = MemberRoute,
        label = "회원",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    ),
    SETTINGS(
        route = SettingRoute,
        label = "설정",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
    ),
}
