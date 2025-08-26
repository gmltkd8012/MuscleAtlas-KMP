package com.rebuilding.muscleatlas.main.screen

import android.R.attr.name
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.main.unit.SettingMenuChip
import com.rebuilding.muscleatlas.model.Screen
import com.rebuilding.muscleatlas.model.SettingScreen

@Composable
fun SettingMainScreen(
    onClickMenu: (String) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val menuList = SettingScreen.allSettingScreens

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.color.primary)
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
                   lineColor = AppColors.color.secondary
               )
           }
       }
    }
}