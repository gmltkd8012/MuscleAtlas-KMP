package com.rebuilding.muscleatlas.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.main.component.MainHeaderBar
import com.rebuilding.muscleatlas.main.unit.SettingMenuChip
import com.rebuilding.muscleatlas.model.Screen
import com.rebuilding.muscleatlas.model.SettingScreen

@Composable
fun SettingMainScreen(
    onClickMenu: (String) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val menuList = SettingScreen.allSettingScreens

    Scaffold(
        topBar = {
            MainHeaderBar(
                title = Screen.Setting.label,
                isNeedAdd = false,
            )
        },
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                items(
                    count = menuList.size
                ) { index ->
                    SettingMenuChip(
                        route = menuList[index].route,
                        name = menuList[index].label,
                        icon = menuList[index].icon,
                        onClick = { route ->
                            onClickMenu(route)
                        }
                    )

                    if (index != menuList.size - 1) {
                        BaseLine(
                            lineColor = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}