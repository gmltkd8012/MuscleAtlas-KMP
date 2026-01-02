package com.rebuilding.muscleatlas.app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
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
import com.rebuilding.muscleatlas.workout.navigation.workoutScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    onNavigateToAccount: () -> Unit = {},
    onNavigateToWorkoutDetail: (exerciseId: String) -> Unit,
    onNavigateToMemberDetail: (memberId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentTitle = BottomNavItem.entries.find { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
    }?.label ?: "메뉴"

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
            enterTransition = {
                val direction = getSlideDirection(initialState, targetState)
                slideIntoContainer(
                    towards = direction,
                    animationSpec = tween(300),
                )
            },
            exitTransition = {
                val direction = getSlideDirection(initialState, targetState)
                slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(300),
                )
            },
            popEnterTransition = {
                val direction = getSlideDirection(initialState, targetState)
                slideIntoContainer(
                    towards = direction,
                    animationSpec = tween(300),
                )
            },
            popExitTransition = {
                val direction = getSlideDirection(initialState, targetState)
                slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(300),
                )
            },
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

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
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

/**
 * 탭 순서에 따른 슬라이드 방향 결정
 * 운동(0) - 회원(1) - 설정(2)
 *
 * 인덱스가 증가하는 방향(왼쪽→오른쪽 탭) = 화면이 왼쪽으로 슬라이드 (Start)
 * 인덱스가 감소하는 방향(오른쪽→왼쪽 탭) = 화면이 오른쪽으로 슬라이드 (End)
 */
private fun getSlideDirection(
    initialState: NavBackStackEntry,
    targetState: NavBackStackEntry,
): AnimatedContentTransitionScope.SlideDirection {
    val initialIndex = BottomNavItem.entries.indexOfFirst { item ->
        initialState.destination.hasRoute(item.route::class)
    }
    val targetIndex = BottomNavItem.entries.indexOfFirst { item ->
        targetState.destination.hasRoute(item.route::class)
    }

    return if (targetIndex > initialIndex) {
        AnimatedContentTransitionScope.SlideDirection.Start // 우측에서 좌측으로
    } else {
        AnimatedContentTransitionScope.SlideDirection.End // 좌측에서 우측으로
    }
}


