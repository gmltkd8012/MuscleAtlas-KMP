package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseLine
import com.rebuilding.muscleatlas.setting.unit.SettingMenuChip

@Composable
fun SettingScreen() {
    val listState = rememberLazyListState()
    val testSize = 5 //TODO - 데이터 연동 시, 삭제 예정

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.primary)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
       items(
           count = testSize
       ) { index ->
           SettingMenuChip(
               name = "설정 메뉴 $index",
               icon = Icons.Default.Settings,
           )

           if (index != testSize - 1) {
               BaseLine(
                   lineColor = AppColors.secondary
               )
           }
       }
    }
}