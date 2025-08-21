package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText

@Composable
fun WorkoutManageScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.primary)
        , contentAlignment = Alignment.Center
    ) {
        BaseText(
            text = "운동 종목 관리 페이지",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight(500)
            ),
            color = AppColors.onSecondary
        )
    }
}