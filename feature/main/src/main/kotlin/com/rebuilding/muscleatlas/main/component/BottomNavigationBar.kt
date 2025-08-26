package com.rebuilding.muscleatlas.main.component

import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.component.NavButton
import com.rebuilding.muscleatlas.model.Screen
import kotlin.collections.forEach

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<Screen>
) {
    NavigationBar(
        containerColor = AppColors.color.secondary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination: NavDestination? = navBackStackEntry?.destination

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentDestination?.route == item.route

                NavButton (
                    icon = item.icon,
                    title = item.label,
                    isSelected = selected,
                ) {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}

data class BottomNavItem (
    val route: String,
    val label: String,
    val icon: ImageVector,
)