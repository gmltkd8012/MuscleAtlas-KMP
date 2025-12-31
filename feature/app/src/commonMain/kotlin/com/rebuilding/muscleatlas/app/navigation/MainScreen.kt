package com.rebuilding.muscleatlas.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rebuilding.muscleatlas.member.navigation.MemberRoute
import com.rebuilding.muscleatlas.member.navigation.memberScreen
import com.rebuilding.muscleatlas.setting.navigation.settingScreen
import com.rebuilding.muscleatlas.workout.navigation.WorkoutRoute
import com.rebuilding.muscleatlas.workout.navigation.workoutScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    onNavigateToAccount: () -> Unit = {},
    onNavigateToWorkoutDetail: () -> Unit,
    onNavigateToMemberDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentTitle = BottomNavItem.entries.find { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
    }?.label ?: "메뉴"

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(currentTitle) },
            )
        },
        bottomBar = {
            MuscleAtlasBottomBar(navController = navController)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MemberRoute,
            modifier = Modifier.padding(innerPadding),
        ) {
            // Workout Tab
            workoutScreen(
                onNavigateToDetail = onNavigateToWorkoutDetail,
            )

            // Member Tab
            memberScreen(
                onNavigateToDetail = onNavigateToMemberDetail,
            )

            // Settings Tab
            settingScreen(
                onNavigateToAccount = onNavigateToAccount,
            )
        }
    }
}

@Composable
private fun MuscleAtlasBottomBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        BottomNavItem.entries.forEach { item ->
            val selected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                    )
                },
                label = {
                    Text(text = item.label)
                },
            )
        }
    }
}


